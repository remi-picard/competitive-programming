package com.mbouchenoire.competitive.programming.hashcode.qualification2015.impl;

import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeLogger;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeSolution;
import com.mbouchenoire.competitive.programming.hashcode.qualification2015.model.*;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeAlgorithm;

import java.util.*;
import java.util.stream.Collectors;

public class Algorithm implements HashCodeAlgorithm<InputValue> {

    public HashCodeSolution run(InputValue input, HashCodeLogger logger) {
        final List<Row> rows = input.rows;
        final List<Pool> pools = input.pools;
        final List<Server> servers = input.servers;

        final List<Server> handledServers = new ArrayList<>(servers.size());
        final List<Server> slottedServers = new ArrayList<>(servers.size());

        while (handledServers.size() < servers.size()) {
            final Server bestServer = servers.stream()
                    .filter(server -> !handledServers.contains(server))
                    .min(Comparator
                            .comparing(Server::getValue).reversed()
                            .thenComparing(Server::getCapacity).reversed()
                            .thenComparing(Server::getSize).reversed())
                    .orElseThrow(IllegalStateException::new);

            final Optional<Row> bestRow = rows.stream()
                    .filter(row -> row.canFit(bestServer))
                    .min(Comparator
                            .comparing(Row::getFreeSlotCount).reversed()
                            .thenComparing((Row row) -> row.findPerfectSlot(bestServer).isPresent()).reversed()
                            .thenComparing(Row::getCapacity));

            bestRow.ifPresent(row -> {
                row.addServer(bestServer);
                slottedServers.add(bestServer);
            });

            handledServers.add(bestServer);

            System.out.println("========================================================================");

            for (Row row: rows) {
                row.print();
            }

            System.out.println("======================== (slotted servers: " + slottedServers.size() + "/" + handledServers.size() + ") ====================");
        }

        final List<Server> nonPooledServers = slottedServers.stream()
                .sorted(Comparator.comparing(Server::getValue))
                .collect(Collectors.toList());

        final List<Server> pooledServers = new ArrayList<>(slottedServers.size());

        while (pooledServers.size() < slottedServers.size()) {
            final Pool weakestPool = pools.stream()
                    .min(Comparator
                            .comparing((Pool pool) -> pool.getLowestGuaranteedCapacity(rows))
                            .thenComparing(pool -> pool.getServers().size()))
                    .orElseThrow(IllegalStateException::new);

            final int worstPoolGuaranteedCapcity = weakestPool.getLowestGuaranteedCapacity(rows);

            int bestGain = 0;
            Server bestServer = nonPooledServers.get(0);

            for (Server nonPooledServer: nonPooledServers) {
                final Pool worstPoolWithServer = weakestPool.withServer(nonPooledServer);
                final int updatedPoolGuaranteedCapacity = worstPoolWithServer.getLowestGuaranteedCapacity(rows);
                final int gain = updatedPoolGuaranteedCapacity - worstPoolGuaranteedCapcity;

                if (gain > bestGain) {
                    bestGain = gain;
                    bestServer = nonPooledServer;
                }
            }

            weakestPool.addServer(bestServer);
            nonPooledServers.remove(bestServer);
            pooledServers.add(bestServer);
        }

        final int score = pools.stream()
                .min(Comparator.comparingInt(pool -> pool.getLowestGuaranteedCapacity(rows)))
                .map(pool -> pool.getLowestGuaranteedCapacity(rows))
                .orElseThrow(IllegalStateException::new);

        System.out.println("---> SCORE=" + score);

        final List<String> lines = servers.stream()
                .sorted(Comparator.comparingInt(Server::getId))
                .map(server -> {
                    final Optional<Row> optionalServerRow = rows.stream()
                            .filter(row -> row.contains(server))
                            .findFirst();

                    if (optionalServerRow.isPresent()) {
                        final Row serverRow = optionalServerRow.orElseThrow(IllegalStateException::new);
                        final Slot serverSlot = serverRow.getServerSlot(server);
                        final Pool serverPool = pools.stream().filter(pool -> pool.contains(server)).findFirst().orElseThrow(IllegalStateException::new);
                        return serverRow.getId() + " " + serverSlot.getNumber() + " " + serverPool.getId();
                    } else {
                        return "x";
                    }
                })
                .collect(Collectors.toList());

        return new HashCodeSolution(lines, score);
    }
}
