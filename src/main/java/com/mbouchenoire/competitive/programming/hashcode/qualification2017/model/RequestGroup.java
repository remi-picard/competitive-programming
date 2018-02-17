package com.mbouchenoire.competitive.programming.hashcode.qualification2017.model;

public class RequestGroup {

    public final int count;
    public final Video video;
    public final Endpoint endpoint;

    public RequestGroup(int count, Video video, Endpoint endpoint) {
        this.count = count;
        this.video = video;
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "RequestGroup{" +
                "count=" + count +
                ", video=" + video +
                ", endpoint=" + endpoint +
                '}';
    }
}
