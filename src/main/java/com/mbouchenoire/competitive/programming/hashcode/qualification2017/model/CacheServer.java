package com.mbouchenoire.competitive.programming.hashcode.qualification2017.model;

import java.util.*;

public class CacheServer {

    public final int id;
    public final int size;
    public final List<Video> videos;

    public CacheServer(int id, int size) {
        this(id, size, new ArrayList<>());
    }

    private CacheServer(int id, int size, List<Video> videos) {
        this.id = id;
        this.size = size;
        this.videos = new ArrayList<>(videos);
    }

    public CacheServer clone() {
        return new CacheServer(id, size, new ArrayList<>(videos));
    }

    public int leftSpace() {
        return size - videos.stream().mapToInt(video -> video.size).sum();
    }

    public CacheServer withoutRandomRemovedVideo() {
        if (this.videos.isEmpty()) {
            return this;
        }

        final List<Video> newVideos = new ArrayList<>(videos);

        final int videoToRemoveIndex = new Random().nextInt(newVideos.size());
        newVideos.remove(videoToRemoveIndex);

        return new CacheServer(id, size, newVideos);
    }

    public void addVideo(Video video) {
        if (this.leftSpace() < video.size) {
            throw new IllegalArgumentException("no left space");
        }

        this.videos.add(video);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheServer that = (CacheServer) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CacheServer{" +
                "id=" + id +
                ", size=" + size +
                ", videos=" + videos.size() +
                ", leftSpace=" + leftSpace() +
                '}';
    }
}
