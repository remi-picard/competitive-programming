package com.mbouchenoire.competitive.programming.hashcode.qualification2017.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Output {

    private final List<CacheServer> cacheServers;

    public Output(List<CacheServer> cacheServers) {
        this.cacheServers = new ArrayList<>(cacheServers);
    }

    public List<CacheServer> getCacheServers() {
        return Collections.unmodifiableList(cacheServers);
    }

    @Override
    public String toString() {
        return "Output{" +
                "cacheServers=" + Arrays.toString(cacheServers.toArray()) +
                '}';
    }
}
