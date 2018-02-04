package com.mbouchenoire.competitive.programming.common.model.graph;

@FunctionalInterface
public interface DoubleBFSNextLevelValueIterator {

    double nextInterationValue(double value, int iteration);
}
