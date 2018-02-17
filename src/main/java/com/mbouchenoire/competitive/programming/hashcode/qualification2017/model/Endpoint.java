package com.mbouchenoire.competitive.programming.hashcode.qualification2017.model;

import java.util.*;

public class Endpoint {

    public final int id;
    public final int dataCenterLatency;
    public final Map<CacheServer, Integer> cacheLantencies;

    public Endpoint(int id, int dataCenterLatency, Map<CacheServer, Integer> cacheLantencies) {
        this.id = id;
        this.dataCenterLatency = dataCenterLatency;
        this.cacheLantencies = new HashMap<>(cacheLantencies);
    }

    public Integer latency(CacheServer cacheServer) {
        return cacheLantencies.get(cacheServer);
    }

    public int bestLatency(Video video, List<CacheServer> cacheServers) {
        final OptionalInt minCacheLatency = cacheServers.parallelStream()
                .filter(cacheServer -> cacheServer.videos.contains(video))
                .mapToInt(cacheServer -> cacheLantencies.getOrDefault(cacheServer, dataCenterLatency))
                .min();

        return minCacheLatency.orElse(dataCenterLatency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return id == endpoint.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "id=" + id +
                ", dataCenterLatency=" + dataCenterLatency +
                ", cacheLantencies=" + cacheLantencies +
                '}';
    }
}
