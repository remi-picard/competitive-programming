package com.mbouchenoire.competitive.programming.hashcode.qualification2018.model;

import java.util.ArrayList;
import java.util.List;

public class InputValue {

    public final List<Vehicule> vehicules;
    public final List<Ride> rides;
    public final Grid grid;
    public final int bonus;
    public final int stepCount;

    public InputValue(List<Vehicule> vehicules, List<Ride> rides, Grid grid, int bonus, int stepCount) {
        this.vehicules = new ArrayList<>(vehicules);
        this.rides = new ArrayList<>(rides);
        this.grid = grid;
        this.bonus = bonus;
        this.stepCount = stepCount;
    }

    @Override
    public String toString() {
        return "InputValue{" +
                "vehicules=" + vehicules.size() +
                ", rides=" + rides.size() +
                ", grid=" + grid +
                ", bonus=" + bonus +
                ", stepCount=" + stepCount +
                '}';
    }
}
