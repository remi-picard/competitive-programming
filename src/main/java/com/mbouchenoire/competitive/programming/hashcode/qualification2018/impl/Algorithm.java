package com.mbouchenoire.competitive.programming.hashcode.qualification2018.impl;

import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeAlgorithm;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeLogger;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeSolution;
import com.mbouchenoire.competitive.programming.hashcode.qualification2018.model.InputValue;
import com.mbouchenoire.competitive.programming.hashcode.qualification2018.model.Ride;
import com.mbouchenoire.competitive.programming.hashcode.qualification2018.model.Vehicule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Algorithm implements HashCodeAlgorithm<InputValue> {

    final static Logger LOGGER = LoggerFactory.getLogger(Algorithm.class);

    @Override
    public HashCodeSolution run(InputValue input, HashCodeLogger logger) {

        for (Vehicule vehicule : input.vehicules) {
            while (vehicule.step() <= input.stepCount) {
                final Optional<Ride> optionalBestRide = findBestRide(vehicule, input.rides, input.bonus);

                final Ride bestRide = optionalBestRide.orElse(null);

                if (bestRide != null) {
                    vehicule.ride(bestRide);
                    MDC.put("vehicule-index", String.valueOf(vehicule.index));
                    MDC.put("ride-index", String.valueOf(bestRide.index));
                    this.LOGGER.info("Vehicule {} rides to {}", vehicule.toString(), bestRide.toString());
                } else {
                    break;
                }
            }
        }

        return buildOutput(input.vehicules);
    }

    private static Optional<Ride> findBestRide(Vehicule vehicule, List<Ride> rides, int bonus) {
        return rides.stream()
                .filter(ride -> !ride.isBooked() && vehicule.canRide(ride))
//                .max(Comparator.comparingLong(o -> vehicule.score(o, bonus)));
                .sorted((o1, o2) -> {
                    final long score1 = vehicule.score(o1, bonus);
                    final long score2 = vehicule.score(o2, bonus);

                    if (score1 != score2) {
                        return Long.compare(score1, score2);
                    }
                    return 0;
                })
                .findFirst();
//        final List<Ride> collect = rides.stream()
//                .filter(ride -> !ride.isBooked() && vehicule.canRide(ride))
//                .sorted(Comparator.comparingLong(o -> vehicule.score(o, bonus)))
//                .collect(Collectors.toList());
//
//        return Optional.of(collect.get(0));
    }

    private HashCodeSolution buildOutput(List<Vehicule> vehicules) {
        final List<String> lines = new ArrayList<>(vehicules.size());

        for (Vehicule vehicule : vehicules) {
            final List<Ride> rides = vehicule.rides();

            final StringBuffer sb = new StringBuffer();

            sb.append(rides.size()).append(" ");

            for (Ride ride : rides) {
                sb.append(ride.index).append(" ");
            }

            lines.add(sb.toString());
        }

        return new HashCodeSolution(lines, 0);
    }
}
