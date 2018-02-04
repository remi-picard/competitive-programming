package com.mbouchenoire.competitive.programming.hashcode.qualification2015.model;

import java.util.Objects;

public class UnavailableSlot extends Slot {

    private final int rowNumber;

    public UnavailableSlot(int rowNumber, int number) {
        super(number, false);
        this.rowNumber = rowNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UnavailableSlot that = (UnavailableSlot) o;
        return rowNumber == that.rowNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rowNumber);
    }

    @Override
    public String toString() {
        return "UnavailableSlot{" +
                "rowNumber=" + rowNumber +
                ", number=" + getNumber() +
                '}';
    }
}
