package com.mbouchenoire.competitive.programming.hashcode.qualification2017.impl;

import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeInputValueParser;
import com.mbouchenoire.competitive.programming.hashcode.qualification2017.model.CacheServer;
import com.mbouchenoire.competitive.programming.hashcode.qualification2017.model.Endpoint;
import com.mbouchenoire.competitive.programming.hashcode.qualification2017.model.InputValue;
import com.mbouchenoire.competitive.programming.hashcode.qualification2017.model.RequestGroup;
import com.mbouchenoire.competitive.programming.hashcode.qualification2017.model.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class InputValueParser implements HashCodeInputValueParser<InputValue> {

    @Override
    public InputValue parse(Scanner scanner) {
        final String[] firstLine = scanner.nextLine().split( " ");
        final int videoCount = Integer.parseInt(firstLine[0]);
        final int endpointCount = Integer.parseInt(firstLine[1]);
        final int requestDescriptionCount = Integer.parseInt(firstLine[2]);
        final int cacheServerCount = Integer.parseInt(firstLine[3]);
        final int cacheServerSize = Integer.parseInt(firstLine[4]);

        final String[] videoLine = scanner.nextLine().split(" ");

        final List<Video> videos = new ArrayList<>(videoCount);

        for (int videoId = 0; videoId < videoLine.length; videoId++) {
            final int videoSize = Integer.parseInt(videoLine[videoId]);
            final Video video = new Video(videoId, videoSize);
            videos.add(video);
        }

        final List<CacheServer> cacheServers = new ArrayList<>(cacheServerCount);

        for (int cacheServerId = 0; cacheServerId < cacheServerCount; cacheServerId++) {
            final CacheServer cacheServer = new CacheServer(cacheServerId, cacheServerSize);
            cacheServers.add(cacheServer);
        }

        final List<Endpoint> endpoints = new ArrayList<>(endpointCount);

        for (int endpointIndex = 0; endpointIndex < endpointCount; endpointIndex++) {
            final String[] endpointFirstLine = scanner.nextLine().split(" ");
            final int dataCenterLatency = Integer.parseInt(endpointFirstLine[0]);
            final int cacheCount = Integer.parseInt(endpointFirstLine[1]);

            final Map<CacheServer, Integer> latencies = new ConcurrentHashMap<>(cacheCount);

            for (int cacheServerLineId = 0; cacheServerLineId < cacheCount; cacheServerLineId++) {
                final String[] cacheServerLine = scanner.nextLine().split(" ");
                final int cacheServerId = Integer.parseInt(cacheServerLine[0]);
                final int cacheServerLatency = Integer.parseInt(cacheServerLine[1]);
                latencies.put(cacheServers.stream().filter(cacheServer -> cacheServer.id == cacheServerId).findFirst().get(), cacheServerLatency);
            }

            final Endpoint endpoint = new Endpoint(endpointIndex, dataCenterLatency, latencies);
            endpoints.add(endpoint);
        }

        final List<RequestGroup> requestGroups = new ArrayList<>(requestDescriptionCount);

        for (int i = 0; i < requestDescriptionCount; i++) {
            final String[] requestDescriptionLine = scanner.nextLine().split(" ");
            final int videoId = Integer.parseInt(requestDescriptionLine[0]);
            final int endpointId = Integer.parseInt(requestDescriptionLine[1]);
            final int requestCount = Integer.parseInt(requestDescriptionLine[2]);

            final Video video = videos.stream().filter(v -> v.id == videoId).findFirst().get();
            final Endpoint endpoint = endpoints.stream().filter(e -> e.id == endpointId).findFirst().get();
            final RequestGroup requestGroup = new RequestGroup(requestCount, video, endpoint);
            requestGroups.add(requestGroup);
        }

        return new InputValue(videos, cacheServers, requestGroups);
    }
}
