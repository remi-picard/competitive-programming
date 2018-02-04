package com.mbouchenoire.competitive.programming.hashcode.qualification2015.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Row {

    private final int id;
    private final List<Slot> slots;
    private final List<Server> servers;

    public Row(int id, List<Slot> slots) {
        this.id = id;
        this.slots = new ArrayList<>(slots);
        this.servers = new ArrayList<>(slots.size());
    }

    public int getId() {
        return id;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void addServer(Server server) {
        final Slot firstFreeSlot = findPerfectSlot(server).orElse(findFreeSlot(server).orElseThrow(IllegalStateException::new));

        for (int slotOffset = 0; slotOffset < server.getSize(); slotOffset++) {
            final int offsetedSlotIndex = firstFreeSlot.getNumber() + slotOffset;
            final Slot slot = slots.get(offsetedSlotIndex);

            if (!slot.isAvailable()) {
                throw new IllegalStateException("unavailable slot");
            }

            slot.addServer(server);
        }

        this.servers.add(server);
    }

    public boolean contains(Server server) {
        return servers.contains(server);
    }

    public int getFreeSlotCount() {
        return (int) this.slots.stream().filter(Slot::isAvailable).count();
    }


    public boolean canFit(Server server) {
        return this.findFreeSlot(server).isPresent();
    }

    public int getCapacity() {
        return this.servers().stream().mapToInt(server -> server == null ? 0 : server.getCapacity()).sum();
    }

    public void print() {
        final List<String> strings = this.getSlots().stream()
                .map(slot -> {
                    final String content = slot.getServer() != null ? "o" : !slot.isAvailable() ? "x" : " ";
                    return "[" + content + "]";
                })
                .collect(Collectors.toList());

        final String rowString = String.join("", strings);

        final String finalString = String.format("%s = capacity: %d, free slots: %d/%d",
                rowString,
                getCapacity(),
                getFreeSlotCount(),
                getSlots().size() - getSlots().stream().filter(Slot::isBroken).count()
        );

        System.out.println(finalString);
    }

    public Optional<Slot> findPerfectSlot(Server server) {
        List<Slot> currentStreak = new ArrayList<>();

        for (final Slot slot : this.slots) {
            final boolean isAvailableSlot = slot.isAvailable();

            if (isAvailableSlot) {
                currentStreak.add(slot);
            } else {
                if (currentStreak.size() == server.getSize()) {
                    return Optional.of(currentStreak.get(0));
                }

                currentStreak = new ArrayList<>();
            }
        }

        return Optional.empty();
    }

    private List<Server> servers() {
        return this.slots.stream().map(Slot::getServer).distinct().collect(Collectors.toList());
    }

    public Slot getServerSlot(Server server) {
        for (Slot slot: slots) {
            if (server.equals(slot.getServer())) {
                return slot;
            }
        }

        return null;
    }

    private Optional<Slot> findFreeSlot(Server server) {
        for (int slotIndex = 0; slotIndex < slots.size(); slotIndex++) {
            final Slot slot = slots.get(slotIndex);
            boolean slotIndexOk = true;

            for (int slotOffset = 0; slotOffset < server.getSize(); slotOffset++) {
                final int offsetedSlotIndex = slotIndex + slotOffset;

                try {
                    final Slot offsetedSlot = slots.get(offsetedSlotIndex);

                    if (!offsetedSlot.isAvailable()) {
                        slotIndexOk = false;
                    }
                } catch (Exception e) {
                    slotIndexOk = false;
                }
            }

            if (slotIndexOk) {
                return Optional.of(slot);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return id == row.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", slots=" + slots.size() +
                ", freeSlotCount=" + getFreeSlotCount() +
                '}';
    }
}
