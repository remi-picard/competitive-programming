package com.mbouchenoire.competitive.programming.hashcode.qualification2015.model;

import java.util.Objects;

public class Slot {

    private final int number;
    private final boolean broken;

    private Server server;

    public Slot(int number, boolean available) {
        this.number = number;
        this.broken = !available;
    }

    public int getNumber() {
        return number;
    }

    public void addServer(Server server) {
        this.server = server;
    }

    public boolean isBroken() {
        return broken;
    }

    public boolean isAvailable() {
        return !broken && (this.server == null);
    }

    public Server getServer() {
        return server;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return number == slot.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Slot{" +
                "number=" + number +
                ", broken=" + broken +
                ", available=" + isAvailable() +
                ", server=" + server +
                '}';
    }
}
