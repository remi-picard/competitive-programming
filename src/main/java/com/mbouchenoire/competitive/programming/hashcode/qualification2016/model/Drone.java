package com.mbouchenoire.competitive.programming.hashcode.qualification2016.model;

import com.mbouchenoire.competitive.programming.common.model.geometry.Coord;

import java.util.Objects;

public class Drone {

    private final int id;
    private final Coord coord;

    public Drone(int id, Coord coord) {
        this.id = id;
        this.coord = coord;
    }

    public int getId() {
        return id;
    }

    public Coord getCoord() {
        return coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return id == drone.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", coord=" + coord +
                '}';
    }
}
