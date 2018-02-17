package com.mbouchenoire.competitive.programming.hashcode.common;

public class HashCodeInput<T> {

    private final HashCodeSession session;
    private final String name;
    private final T value;

    public HashCodeInput(HashCodeSession session, String name, T value) {
        this.session = session;
        this.name = name;
        this.value = value;
    }

    public HashCodeSession getSession() {
        return session;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
