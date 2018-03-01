package com.mbouchenoire.competitive.programming.hashcode.qualification2018.model;

public class Grid {

    public final int rowCount;
    public final int columnCount;

    public Grid(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "rows=" + rowCount +
                ", columns=" + columnCount +
                '}';
    }
}
