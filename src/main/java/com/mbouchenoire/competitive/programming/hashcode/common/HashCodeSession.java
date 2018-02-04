package com.mbouchenoire.competitive.programming.hashcode.common;

public class HashCodeSession {

    private final int year;
    private final HashCodePhase phase;

    public HashCodeSession(int year, HashCodePhase phase) {
        this.year = year;
        this.phase = phase;
    }

    public int getYear() {
        return year;
    }

    public HashCodePhase getPhase() {
        return phase;
    }
}
