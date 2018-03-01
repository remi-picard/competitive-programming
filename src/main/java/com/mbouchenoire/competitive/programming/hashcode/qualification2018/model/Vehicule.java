package com.mbouchenoire.competitive.programming.hashcode.qualification2018.model;

import com.mbouchenoire.competitive.programming.common.model.geometry.Coord;

import java.util.ArrayList;
import java.util.List;

public class Vehicule {

    public final int index;
    private Coord coord;
    private List<Ride> rides;
    private int step;

    public Vehicule(int index) {
        this.index = index;
        this.coord = new Coord(0, 0);
        this.rides = new ArrayList<>();
        this.step = 0;
    }

    public long score(Ride ride, int bonus) {
        if (!this.canRide(ride)) {
            return Integer.MIN_VALUE;
        }

        final long distanceToRideStart = this.coord.distanceGrid(ride.start);
        final long stepAtRideStart = this.step + distanceToRideStart;
        final long waitingTime = Math.max(ride.earliestStart - stepAtRideStart, 0);

        final int effectiveBonus = waitingTime <= 0 ? bonus : 0;
        final int availability = Math.abs(ride.latestFinish - ride.earliestStart);

//        return Long.MAX_VALUE;
//        return Long.MAX_VALUE - (distanceToRideStart + waitingTime);

//        return Long.MAX_VALUE - ride.earliestStart;
        return ride.distance() - distanceToRideStart - waitingTime;
//        return ((ride.distance() * 10) - (distanceToRideStart + waitingTime) + effectiveBonus) - availability;
    }

    private long landingStep(Ride ride) {
        final long distanceToRideStart = this.coord.distanceGrid(ride.start);
        final long stepAtRideStart = step + distanceToRideStart;
        final long waitingTime = Math.max(ride.earliestStart - stepAtRideStart, 0);
        final long distanceRide = ride.start.distanceGrid(ride.end);
        return (this.step + distanceToRideStart + waitingTime + distanceRide);
    }

    public int step() {
        return this.step;
    }

    public boolean canRide(Ride ride) {
        final long landingStep = this.landingStep(ride);
        return (landingStep <= ride.latestFinish);
    }

    public void ride(Ride ride) {
        if (this.rides.contains(ride)) {
            throw new IllegalStateException(this + " already has this ride booked: " + ride);
        }

        if (!canRide(ride)) {
            throw new IllegalStateException(this + " will be to late for ride: " + ride);
        }

        final long landingStep = this.landingStep(ride);

        this.step = (int)landingStep;
        this.rides.add(ride);
        ride.rider(this);
    }

    public List<Ride> rides() {
        return new ArrayList<>(this.rides);
    }
}
