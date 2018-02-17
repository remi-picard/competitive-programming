package com.mbouchenoire.competitive.programming.hashcode.qualification2017.model;

import java.util.Objects;

public class CachedVideo {

    public final Video video;
    public final CacheServer cacheServer;

    public CachedVideo(Video video, CacheServer cacheServer) {
        this.video = video;
        this.cacheServer = cacheServer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CachedVideo that = (CachedVideo) o;
        return Objects.equals(video, that.video) &&
                Objects.equals(cacheServer, that.cacheServer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(video, cacheServer);
    }

    @Override
    public String toString() {
        return "CachedVideo{" +
                "video=" + video +
                ", cacheServer=" + cacheServer +
                '}';
    }
}
