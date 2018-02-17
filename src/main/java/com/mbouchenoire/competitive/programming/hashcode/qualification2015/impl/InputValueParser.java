package com.mbouchenoire.competitive.programming.hashcode.qualification2015.impl;

import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeInputValueParser;
import com.mbouchenoire.competitive.programming.hashcode.qualification2015.model.InputValue;
import com.mbouchenoire.competitive.programming.hashcode.qualification2015.model.Pool;
import com.mbouchenoire.competitive.programming.hashcode.qualification2015.model.Row;
import com.mbouchenoire.competitive.programming.hashcode.qualification2015.model.Server;
import com.mbouchenoire.competitive.programming.hashcode.qualification2015.model.Slot;
import com.mbouchenoire.competitive.programming.hashcode.qualification2015.model.UnavailableSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputValueParser implements HashCodeInputValueParser<InputValue> {

    @Override
    public InputValue parse(Scanner scanner) {
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

        return new InputValue(rows, pools, servers);
    }
}
