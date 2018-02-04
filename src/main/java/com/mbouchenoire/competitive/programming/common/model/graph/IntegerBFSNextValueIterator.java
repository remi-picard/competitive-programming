package com.mbouchenoire.competitive.programming.common.model.graph;

@FunctionalInterface
public interface IntegerBFSNextValueIterator {

    int nextInterationValue(int value, int iteration);
}
