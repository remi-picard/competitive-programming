package com.mbouchenoire.competitive.programming.hashcode.qualification2015.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Pool {

    private final int id;
    private final List<Server> servers;

    public Pool(int id) {
        this(id, new ArrayList<>());
    }

    private Pool(int id, List<Server> servers) {
        this.id = id;
        this.servers = new ArrayList<>(servers);
    }

    public int getId() {
        return id;
    }

    public List<Server> getServers() {
        return Collections.unmodifiableList(servers);
    }

    public boolean contains(Server server) {
        return servers.contains(server);
    }

    public void addServer(Server server) {
        this.servers.add(server);
    }

    public int getLowestGuaranteedCapacity(List<Row> rows) {
        final List<Row> poolRows = servers.stream()
                .filter(Objects::nonNull)
                .map(server -> findServerRow(server, rows))
                .distinct()
                .collect(Collectors.toList());

        int lowestCapacity = Integer.MIN_VALUE;

        for (Row poolRow: poolRows) {
            int capacityWithoutPoolRow = 0;

            for (Row brokenRow: poolRows) {
                if (!brokenRow.equals(poolRow)) {
                    for (Server server: this.servers) {
                        if (brokenRow.contains(server)) {
                            capacityWithoutPoolRow += server.getCapacity();
                        }
                    }
                }
            }

            if (lowestCapacity == Integer.MIN_VALUE || capacityWithoutPoolRow < lowestCapacity) {
                lowestCapacity = capacityWithoutPoolRow;
            }
        }

        return lowestCapacity == Integer.MIN_VALUE ? 0 : lowestCapacity;
    }

    private static Row findServerRow(Server server, List<Row> rows) {
        for (Row row: rows) {
            for (Slot slot: row.getSlots()) {
                if (server.equals(slot.getServer())) {
                    return row;
                }
            }
        }

        throw new IllegalStateException("cannot find server in rows");
    }

    public Pool withServer(Server server) {
        final List<Server> newServers = new ArrayList<>(servers);
        newServers.add(server);
        return new Pool(id, newServers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pool pool = (Pool) o;
        return id == pool.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pool{" +
                "id=" + id +
                ", servers=" + servers.size() +
                '}';
    }
}
