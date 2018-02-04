package com.mbouchenoire.competitive.programming.common.model.container;

import java.util.Map;
import java.util.Objects;

public class Pair<F, S> implements Map.Entry<F, S> {

    private final F first;
    private S second;

    public Pair(F first, S second) {
        Objects.requireNonNull(first, "first");

        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public F getKey() {
        return first;
    }

    @Override
    public S getValue() {
        return second;
    }

    @Override
    public S setValue(S value) {
        final S oldValue = this.second;
        this.second = value;
        return oldValue;
    }
}
