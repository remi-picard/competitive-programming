package com.mbouchenoire.competitive.programming.hashcode.common;

public interface HashCodeAlgorithm<T> {

    HashCodeSolution run(T input, HashCodeLogger logger);
}
