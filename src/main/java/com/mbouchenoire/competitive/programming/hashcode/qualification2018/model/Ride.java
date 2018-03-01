package com.mbouchenoire.competitive.programming.hashcode.qualification2018.model;

import com.mbouchenoire.competitive.programming.common.model.geometry.Coord;

public class Ride {

    public final int index;
    public final Coord start;
    public final Coord end;
    public final int earliestStart;
    public final int latestFinish;

    private Vehicule rider;

    public Ride(int index, Coord start, Coord end, int earliestStart, int latestFinish) {
        this.index = index;
        this.start = start;
        this.end = end;
        this.earliestStart = earliestStart;
        this.latestFinish = latestFinish;
        this.rider = null;
    }

    public long distance() {
        return this.start.distanceGrid(this.end);
    }

    public void rider(Vehicule vehicule) {
        this.rider = vehicule;
    }

    public boolean isBooked() {
        return this.rider != null;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "index=" + index +
                ", start=" + start +
                ", end=" + end +
                ", earliestStart=" + earliestStart +
                ", latestFinish=" + latestFinish +
                '}';
    }
}
