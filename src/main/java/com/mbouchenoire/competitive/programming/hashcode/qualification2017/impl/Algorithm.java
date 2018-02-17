package com.mbouchenoire.competitive.programming.hashcode.qualification2017.impl;

import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeAlgorithm;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeLogger;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeSolution;
import com.mbouchenoire.competitive.programming.hashcode.qualification2017.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Algorithm implements HashCodeAlgorithm<InputValue> {

    private static Map<CachedVideo, Integer> cachedVideoScores = new ConcurrentHashMap<>();

    @Override
    public HashCodeSolution run(InputValue input, HashCodeLogger logger) {
        final List<CacheServer> cacheServers = input.cacheServers;
        final List<Video> videos = input.videos;
        final List<RequestGroup> requestGroups = input.requestGroups;

        final List<CacheServer> sortedCacheServers = new ArrayList<>(cacheServers);
        final List<Video> sortedVideos = new ArrayList<>(videos);
        final AtomicInteger processedCacheServerCount = new AtomicInteger(0);

        sortedCacheServers.parallelStream().forEach(cacheServer -> {
            logger.info("starting to process: " + cacheServer);

            final List<Video> bestCachedVideos = sortedVideos.parallelStream()
                    .sorted((video1, video2) -> {
                        final int score1 = score(video1, cacheServer, requestGroups);
                        final int score2 = score(video2, cacheServer, requestGroups);
                        return Integer.compare(score2, score1);
                    })
                    .collect(Collectors.toList());

            boolean canFitVideo = true;

            while (canFitVideo) {
                final List<Video> okVideos = bestCachedVideos.stream()
                        .filter(video -> cacheServer.leftSpace() >= video.size && !cacheServer.videos.contains(video)).collect(Collectors.toList());

                canFitVideo = !okVideos.isEmpty();

                if (canFitVideo) {
                    final Video video = okVideos.get(0);
                    cacheServer.addVideo(video);
                }
            }

            processedCacheServerCount.addAndGet(1);
            logger.info("%d/%d processed cache servers (last filled: %s)", processedCacheServerCount.get(), cacheServers.size(), cacheServer);
        });

        final int score = score(cacheServers, requestGroups);

        return new HashCodeSolution(new ArrayList<>(), score);
    }

    private static int score(List<CacheServer> cacheServers, List<RequestGroup> requestGroups) {
        final AtomicInteger totalRequestCount = new AtomicInteger(0);

        final int latencyScore = requestGroups.parallelStream()
                .mapToInt(requestGroup -> {
                    totalRequestCount.addAndGet(requestGroup.count);
                    final int bestLatency = requestGroup.endpoint.bestLatency(requestGroup.video, cacheServers);
                    final int latencyGain = requestGroup.endpoint.dataCenterLatency - bestLatency;
                    return latencyGain * requestGroup.count;
                })
                .sum();

        return (latencyScore / totalRequestCount.get()) * 1000;
    }

    private static int score(Video video, CacheServer cacheServer, List<RequestGroup> requestGroups) {
        final CachedVideo cachedVideo = new CachedVideo(video, cacheServer);

        if (cachedVideoScores.containsKey(cachedVideo)) {
            return cachedVideoScores.get(cachedVideo);
        }

        final int score = requestGroups.parallelStream()
                .mapToInt(requestGroup -> {
                    final Integer cacheLatency = requestGroup.endpoint.latency(cacheServer);
                    int requestGroupVideoScore = 0;

                    if (cacheLatency != null && requestGroup.video.equals(video)) {
                        final int gainedLatency = (requestGroup.endpoint.dataCenterLatency - cacheLatency);
                        requestGroupVideoScore += (requestGroup.count * gainedLatency);
                    }

                    return requestGroupVideoScore;
                })
                .sum();

        cachedVideoScores.put(cachedVideo, score);

        return score;
    }
}
