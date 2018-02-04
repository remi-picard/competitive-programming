package com.mbouchenoire.competitive.programming.hashcode.common;

public enum HashCodePhase {
    QUALIFICATION("qualification");

    private final String name;

    HashCodePhase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
