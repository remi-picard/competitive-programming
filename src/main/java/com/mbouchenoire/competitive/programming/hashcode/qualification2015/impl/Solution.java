package com.mbouchenoire.competitive.programming.hashcode.qualification2015.impl;

import com.mbouchenoire.competitive.programming.hashcode.qualification2015.model.*;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeAlgorithm;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeInput;

import java.util.*;
import java.util.stream.Collectors;

public class Solution implements HashCodeAlgorithm {

    @Override
    public List<String> run(HashCodeInput input) {
        final Scanner scanner = input.getScanner();
        final String[] firstLine = scanner.nextLine().split(" ");
        final int rowCount = Integer.parseInt(firstLine[0]);
        final int slotsPerRow = Integer.parseInt(firstLine[1]);
        final int unavailableSlotCount = Integer.parseInt(firstLine[2]);
        final int poolCount = Integer.parseInt(firstLine[3]);
        final int serverCount = Integer.parseInt(firstLine[4]);

        final List<UnavailableSlot> unavailableSlots = new ArrayList<>(unavailableSlotCount);

        for (int i = 0; i < unavailableSlotCount; i++) {
            final String[] unavailableSlotLine = scanner.nextLine().split(" ");
            final int rowNumber = Integer.parseInt(unavailableSlotLine[0]);
            final int slotNumber = Integer.parseInt(unavailableSlotLine[1]);
            final UnavailableSlot unavailableSlot = new UnavailableSlot(rowNumber, slotNumber);
            unavailableSlots.add(unavailableSlot);
        }

        final List<Row> rows = new ArrayList<>(rowCount);

        for (int i = 0; i < rowCount; i++) {
            final int rowNumber = i;
            final List<Slot> slots = new ArrayList<>(slotsPerRow);

            for (int j = 0; j < slotsPerRow; j++) {
                final int slotNumber = j;
                final boolean available = unavailableSlots.stream().noneMatch(s -> s.getRowNumber() == rowNumber && s.getNumber() == slotNumber);
                final Slot slot = new Slot(slotNumber, available);
                slots.add(slot);
            }

            final Row row = new Row(rowNumber, slots);
            rows.add(row);
        }

        final List<Pool> pools = new ArrayList<>(poolCount);

        for (int i = 0; i < poolCount; i++) {
            pools.add(new Pool(i));
        }

        final List<Server> servers = new ArrayList<>(serverCount);

        for (int serverIndex = 0; serverIndex < serverCount; serverIndex++) {
            final String[] serverLine = scanner.nextLine().split(" ");
            final int serverSize = Integer.parseInt(serverLine[0]);
            final int serverCapacity = Integer.parseInt(serverLine[1]);
            final Server server = new Server(serverIndex, serverSize, serverCapacity);
            servers.add(server);
        }

        final List<Server> handledServers = new ArrayList<>(serverCount);
        final List<Server> slottedServers = new ArrayList<>(serverCount);

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

        return servers.stream()
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
    }
}
