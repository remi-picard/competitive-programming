package com.mbouchenoire.competitive.programming.common.model.graph;

@FunctionalInterface
public interface BFSTraversable<N> {

    boolean canBeVisited(N node);
}
