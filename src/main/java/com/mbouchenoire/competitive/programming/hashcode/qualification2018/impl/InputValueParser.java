package com.mbouchenoire.competitive.programming.hashcode.qualification2018.impl;

import com.mbouchenoire.competitive.programming.common.model.geometry.Coord;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeInputValueParser;
import com.mbouchenoire.competitive.programming.hashcode.qualification2018.model.Grid;
import com.mbouchenoire.competitive.programming.hashcode.qualification2018.model.InputValue;
import com.mbouchenoire.competitive.programming.hashcode.qualification2018.model.Ride;
import com.mbouchenoire.competitive.programming.hashcode.qualification2018.model.Vehicule;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputValueParser implements HashCodeInputValueParser<InputValue> {

    @Override
    public InputValue parse(Scanner scanner) {
        final String[] firstLine = scanner.nextLine().split(" ");
        final int rowCount = Integer.parseInt(firstLine[0]);
        final int columnCount = Integer.parseInt(firstLine[1]);
        final int vehiculeCount = Integer.parseInt(firstLine[2]);
        final int rideCount = Integer.parseInt(firstLine[3]);
        final int bonus = Integer.parseInt(firstLine[4]);
        final int stepCount = Integer.parseInt(firstLine[5]);

        final List<Vehicule> vehicules = new ArrayList<>(vehiculeCount);

        for (int i = 0; i < vehiculeCount; i++) {
            vehicules.add(new Vehicule(i));
        }

        final List<Ride> rides = new ArrayList<>(rideCount);

        for (int rideIndex = 0; rideIndex < rideCount; rideIndex++) {
            final String[] rideLine = scanner.nextLine().split(" ");
            final int rowStart = Integer.parseInt(rideLine[0]);
            final int columnStart = Integer.parseInt(rideLine[1]);
            final int rowFinish = Integer.parseInt(rideLine[2]);
            final int columnFinish = Integer.parseInt(rideLine[3]);
            final int earliestStart = Integer.parseInt(rideLine[4]);
            final int latestFinish = Integer.parseInt(rideLine[5]);

            final Coord start = new Coord(columnStart, rowStart);
            final Coord end = new Coord(columnFinish, rowFinish);
            final Ride ride = new Ride(rideIndex, start, end, earliestStart, latestFinish);

            rides.add(ride);
        }

        final Grid grid = new Grid(rowCount, columnCount);

        return new InputValue(vehicules, rides, grid, bonus, stepCount);
    }
}
