package com.mbouchenoire.competitive.programming.hashcode.qualification2017.impl;

import com.mbouchenoire.competitive.programming.common.impl.genetic.GeneticAlgorithm;
import com.mbouchenoire.competitive.programming.common.model.genetic.CandidateGenerator;
import com.mbouchenoire.competitive.programming.common.model.genetic.CandidateMerger;
import com.mbouchenoire.competitive.programming.common.model.genetic.CandidateMutator;
import com.mbouchenoire.competitive.programming.common.model.genetic.FitnessFunction;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeAlgorithm;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeInput;
import com.mbouchenoire.competitive.programming.hashcode.qualification2017.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class GenticAlgorithm {
//
//    @Override
//    public List<String> run(HashCodeInput input) {
//        final Scanner scanner = input.getScanner();
//
//        final String[] firstLine = scanner.nextLine().split( " ");
//        final int videoCount = Integer.parseInt(firstLine[0]);
//        final int endpointCount = Integer.parseInt(firstLine[1]);
//        final int requestDescriptionCount = Integer.parseInt(firstLine[2]);
//        final int cacheServerCount = Integer.parseInt(firstLine[3]);
//        final int cacheServerSize = Integer.parseInt(firstLine[4]);
//
//        final String[] videoLine = scanner.nextLine().split(" ");
//
//        final List<Video> videos = new ArrayList<>(videoCount);
//
//        for (int videoId = 0; videoId < videoLine.length; videoId++) {
//            final int videoSize = Integer.parseInt(videoLine[videoId]);
//            final Video video = new Video(videoId, videoSize);
//            videos.add(video);
//        }
//
//        final List<CacheServer> cacheServers = new ArrayList<>(cacheServerCount);
//
//        for (int cacheServerId = 0; cacheServerId < cacheServerCount; cacheServerId++) {
//            final CacheServer cacheServer = new CacheServer(cacheServerId, cacheServerSize);
//            cacheServers.add(cacheServer);
//        }
//
//        final List<Endpoint> endpoints = new ArrayList<>(endpointCount);
//
//        for (int endpointIndex = 0; endpointIndex < endpointCount; endpointIndex++) {
//            final String[] endpointFirstLine = scanner.nextLine().split(" ");
//            final int dataCenterLatency = Integer.parseInt(endpointFirstLine[0]);
//            final int cacheCount = Integer.parseInt(endpointFirstLine[1]);
//
//            final Map<CacheServer, Integer> latencies = new HashMap<>(cacheCount);
//
//            for (int cacheServerLineId = 0; cacheServerLineId < cacheCount; cacheServerLineId++) {
//                final String[] cacheServerLine = scanner.nextLine().split(" ");
//                final int cacheServerId = Integer.parseInt(cacheServerLine[0]);
//                final int cacheServerLatency = Integer.parseInt(cacheServerLine[1]);
//                latencies.put(cacheServers.stream().filter(cacheServer -> cacheServer.getId() == cacheServerId).findFirst().get(), cacheServerLatency);
//            }
//
//            final Endpoint endpoint = new Endpoint(endpointIndex, dataCenterLatency, latencies);
//            endpoints.add(endpoint);
//        }
//
//        final List<RequestGroup> requestGroups = new ArrayList<>(requestDescriptionCount);
//
//        for (int i = 0; i < requestDescriptionCount; i++) {
//            final String[] requestDescriptionLine = scanner.nextLine().split(" ");
//            final int videoId = Integer.parseInt(requestDescriptionLine[0]);
//            final int endpointId = Integer.parseInt(requestDescriptionLine[1]);
//            final int requestCount = Integer.parseInt(requestDescriptionLine[2]);
//
//            final Video video = videos.stream().filter(v -> v.getId() == videoId).findFirst().get();
//            final Endpoint endpoint = endpoints.stream().filter(e -> e.getId() == endpointId).findFirst().get();
//            final RequestGroup requestGroup = new RequestGroup(requestCount, video, endpoint);
//            requestGroups.add(requestGroup);
//        }
//
//        final FitnessFunction<Output> fitnessFunction = output -> {
//            int combinedRequestGroupCount = 0;
//            int combinedRequestGroupScore = 0;
//
//            for (RequestGroup requestGroup : requestGroups) {
//                combinedRequestGroupCount += requestGroup.getCount();
//                int bestLatency = requestGroup.getEndpoint().getDataCenterLatency();
//
//                for (CacheServer cacheServer : cacheServers) {
//                    if (cacheServer.getVideos().contains(requestGroup.getVideo())) {
//                        final Integer cacheLatency = requestGroup.getEndpoint().getCacheLatency(cacheServer);
//
//                        if (cacheLatency != null && cacheLatency < bestLatency) {
//                            bestLatency = cacheLatency;
//                        }
//                    }
//                }
//
//                final int gainedLatency = requestGroup.getEndpoint().getDataCenterLatency() - bestLatency;
//                final int requestGroupScore = gainedLatency * requestGroup.getCount();
//                combinedRequestGroupScore += requestGroupScore;
//            }
//
//            return (combinedRequestGroupScore / combinedRequestGroupCount) * 1000;
//        };
//        final GeneticAlgorithm<Output> geneticAlgorithm = new GeneticAlgorithm<>(
//                fitnessFunction,
//                new CandidateGenerator<Output>() {
//                    @Override
//                    public Output generate() {
//                        final List<CacheServer> newCacheServers = cacheServers.stream().map(CacheServer::clone).collect(Collectors.toList());
//
//                        while (anySpaceLeft(newCacheServers, videos)) {
//                            final CacheServer randomCacheServer = randomFreeCacheServer(videos, newCacheServers);
//                            //System.out.println("randomCacheServer: " + randomCacheServer);
//                            final Optional<Video> randomVideo = randomFittingVideo(randomCacheServer.leftSpace(), videos);
//                            //System.out.println("randomVideo: " + randomVideo);
//
//                            randomVideo.ifPresent(randomCacheServer::addVideo);
//                        }
//
//                        final Output output = new Output(newCacheServers);
//
//                        System.out.println("generated output score: " + fitnessFunction.evaluate(output));
//                        System.out.println("generated output: " + output);
//
//                        return output;
//                    }
//                },
//                new CandidateMerger<Output>() {
//                    @Override
//                    public Output merge(Output first, Output second) {
//                        final List<CacheServer> newCacheServers = new ArrayList<>(cacheServerCount);
//
//                        for (int i = 0; i < cacheServerCount; i++) {
//                            final int cacheServerId = i;
//                            final CacheServer firstCacheServer = first.getCacheServers().stream().filter(cacheServer -> cacheServer.getId() == cacheServerId).findFirst().get();
//                            final CacheServer secondCacheServer = second.getCacheServers().stream().filter(cacheServer -> cacheServer.getId() == cacheServerId).findFirst().get();
//
//                            if (new Random().nextBoolean()) {
//                                newCacheServers.add(firstCacheServer);
//                            } else {
//                                newCacheServers.add(secondCacheServer);
//                            }
//                        }
//
//                        final Output output = new Output(newCacheServers);
//
//                        System.out.println("merged output: " + output);
//
//                        return output;
//                    }
//                },
//                new CandidateMutator<Output>() {
//                    @Override
//                    public Output mutate(Output candidate) {
//                        final int VIDEO_TO_REMOVE_COUNT = 3;
//
//                        final List<CacheServer> mutatedCacheServers = new ArrayList<>(candidate.getCacheServers());
//
//                        for (int i = 0; i < VIDEO_TO_REMOVE_COUNT; i++) {
//                            final int randomCacheServerIndex = new Random().nextInt(mutatedCacheServers.size());
//                            final CacheServer randomCacheServer = mutatedCacheServers.get(randomCacheServerIndex);
//
//                            mutatedCacheServers.remove(randomCacheServer);
//
//                            final CacheServer mutatedCacheServer = randomCacheServer.withoutRandomRemovedVideo();
//                            mutatedCacheServers.add(mutatedCacheServer);
//                        }
//
//                        while (anySpaceLeft(mutatedCacheServers, videos)) {
//                            final CacheServer randomCacheServer = randomFreeCacheServer(videos, mutatedCacheServers);
//                            final Optional<Video> randomVideo = randomFittingVideo(randomCacheServer.leftSpace(), videos);
//
//                            randomVideo.ifPresent(randomCacheServer::addVideo);
//                        }
//
//                        final Output output = new Output(mutatedCacheServers);
//
//                        System.out.println("mutated output: " + output);
//
//                        return output;
//                    }
//                });
//
//        geneticAlgorithm.initialize(50);
//
//        geneticAlgorithm.iterate(
//                500,
//                6,
//                2,
//                2,
//                2
//        );
//
//        final Output best = geneticAlgorithm.best();
//
//        System.out.println("SCORE: " + fitnessFunction.evaluate(best));
//
//        return new ArrayList<>();
//    }
//
//    private static boolean anySpaceLeft(List<CacheServer> cacheServers, List<Video> videos) {
//        for (CacheServer cacheServer : cacheServers) {
//            final int cacheServerLeftSpace = cacheServer.leftSpace();
//            final List<Video> cacheServerVideos = cacheServer.getVideos();
//
//            for (Video video : videos) {
//                if (cacheServerLeftSpace >= video.getSize() && !cacheServerVideos.contains(video)) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    private static Optional<Video> randomFittingVideo(int maxSize, List<Video> videos) {
//        final List<Video> fittingVideos = videos.stream().filter(video -> video.getSize() <= maxSize).collect(Collectors.toList());
//
//        if (fittingVideos.isEmpty()) {
//            //System.out.println("no fitting video");
//            return Optional.empty();
//        }
//
//        final int randomVideoIndex = new Random().nextInt(fittingVideos.size());
//
//        return Optional.of(fittingVideos.get(randomVideoIndex));
//    }
//
//    private static CacheServer randomFreeCacheServer(List<Video> videos, List<CacheServer> cacheServers) {
//        final List<CacheServer> freeCacheServers = cacheServers.stream().filter(cacheServer -> {
//            final List<Video> cacheServerVideos = cacheServer.getVideos();
//            final int cacheServerLeftSpace = cacheServer.leftSpace();
//
//            for (Video video : videos) {
//                if (cacheServerLeftSpace >= video.getSize() && !cacheServerVideos.contains(video)) {
//                    return true;
//                }
//            }
//
//            return false;
//        }).collect(Collectors.toList());
//
//        //System.out.println("freeCacheServers: " + freeCacheServers.size());
//        final int randomCacheServerIndex = new Random().nextInt(freeCacheServers.size());
//        return freeCacheServers.get(randomCacheServerIndex);
//    }
}
