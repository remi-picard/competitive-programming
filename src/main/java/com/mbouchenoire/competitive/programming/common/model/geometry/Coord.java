package com.mbouchenoire.competitive.programming.common.model.geometry;

import java.util.Objects;

public class Coord {

    public final long x;
    public final long y;

    public Coord(long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor from a vector A rounding (cast to int) is performed on each x
     * and y doubles of the vector
     *
     * @param v the vector from which you want to create a coord
     */
    public Coord(Vector v) {
        this.x = (long) v.x;
        this.y = (long) v.y;
    }

    /**
     * Adds this coord instance to another one to return the sum of the coords
     *
     * @param coord the other coord to add
     * @return a new instance with coordinate as the sum of this and the given
     * coord
     */
    public Coord add(Coord coord) {
        return new Coord(x + coord.x, y + coord.y);
    }

    /**
     * Return the euclidian distance between two coords.
     *
     * @return sqrt(dx * dx + dy * dy)
     */
    public double distance(Coord coord) {
        return Math.sqrt(distanceSquare(coord));
    }

    /**
     * Returns the euclidian distance square between two coords.
     * <p>
     * Hint : prefer this square distance if you want to compare distances
     * rather than the exact distance that cost more
     *
     * @return dx*dx+dy*dy
     */
    public long distanceSquare(Coord coord) {
        long dx = coord.x - x;
        long dy = coord.y - y;
        return dx * dx + dy * dy;
    }

    public long distanceGrid(Coord coord) {
        long dx = Math.abs(coord.x - x);
        long dy = Math.abs(coord.y - y);
        return dx + dy;
    }

    /**
     * Substracts to the instance x and y the values of the given coord.
     *
     * @param coord the other coord to substract
     * @return a new instance with coordinate as the substraction of this and
     * the given coord
     */
    public Coord minus(Coord coord) {
        return new Coord(x - coord.x, y - coord.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Coord other = (Coord) obj;

        return (x == other.x) && (y == other.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }
}
