package com.mbouchenoire.competitive.programming.hashcode.qualification2017.model;

import java.util.Collections;
import java.util.List;

public class InputValue {

    public final List<Video> videos;
    public final List<CacheServer> cacheServers;
    public final List<RequestGroup> requestGroups;

    public InputValue(List<Video> videos, List<CacheServer> cacheServers, List<RequestGroup> requestGroups) {
        this.videos = Collections.unmodifiableList(videos);
        this.cacheServers = Collections.unmodifiableList(cacheServers);
        this.requestGroups = Collections.unmodifiableList(requestGroups);
    }
}
