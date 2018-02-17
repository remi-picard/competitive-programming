package com.mbouchenoire.competitive.programming.common.model.graph;

public class GraphEdge<N> {

    public final N source;
    public final N destination;
    public final boolean oriented;

    public GraphEdge(N source, N destination, boolean oriented) {
        this.source = source;
        this.destination = destination;
        this.oriented = oriented;
    }

    @Override
    public String toString() {
        return source + (oriented ? " -> " : " <-> ") + destination;
    }
}
