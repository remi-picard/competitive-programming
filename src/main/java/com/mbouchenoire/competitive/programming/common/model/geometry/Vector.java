package com.mbouchenoire.competitive.programming.common.model.geometry;

public class Vector {
    /**
     * Used in the equals method in order to consider two double are "equals"
     */
    public static double COMPARISON_TOLERANCE = 0.0000001;

    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Coord coord) {
        this(coord.x, coord.y);
    }

    public Vector(Vector other) {
        this(other.x, other.y);
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

    /**
     * Negates this vector. The vector has the same magnitude as before, but its direction is now opposite.
     *
     * @return a new vector instance with both x and y negated
     */
    public Vector negate() {
        return new Vector(-x, -y);
    }

    /**
     * Returns a new instance of vector rotated from the given number of degrees.
     *
     * @param degree the number of degrees to rotate
     * @return a new instance rotated
     */
    public Vector rotateInDegree(double degree) {
        return rotateInRadian(Math.toRadians(degree));
    }

    /**
     * Returns a new instance of vector rotated from the given number of radians.
     *
     * @param radians the number of radians to rotate
     * @return a new instance rotated
     */
    public Vector rotateInRadian(double radians) {
        final double length = length();
        double angle = angleInRadian();
        angle += radians;
        final Vector result = new Vector(Math.cos(angle), Math.sin(angle));
        return result.multiply(length);
    }

    /**
     * @return the angle between this vector and the vector (1,0) in degrees
     */
    public double angleInDegree() {
        return Math.toDegrees(angleInRadian());
    }

    /**
     * @return the angle between this vector and the vector (1,0) in radians
     */
    private double angleInRadian() {
        return Math.atan2(y, x);
    }

    /**
     * dot product operator
     * two vectors that are perpendicular have a dot product of 0
     *
     * @param other the other vector of the dot product
     * @return the dot product
     */
    public double dot(Vector other) {
        return x * other.x + y * other.y;
    }

    /**
     * @return the length of the vector
     * Hint: prefer lengthSquare to perform length comparisons
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * @return the square of the length of the vector
     */
    public double lengthSquare() {
        return x * x + y * y;
    }

    /**
     * Return the vector resulting in this vector minus the values of the other vector
     *
     * @param other the instance to substract from this
     * @return a new instance of vector result of the minus operation.
     */
    public Vector minus(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    /**
     * @param factor the double coefficient to multiply the vector with
     * @return return a new instance multiplied by the given factor
     */
    public Vector multiply(double factor) {
        return new Vector(x * factor, y * factor);
    }

    /**
     * @return the new instance normalized from this. A normalized instance has a length of 1
     * If the length of this is 0 returns a (0,0) vector
     */
    public Vector normalized() {
        final double length = length();

        if (length > 0) {
            return new Vector(x / length, y / length);
        }

        return new Vector(0, 0);
    }

    /**
     * Returns the orthogonal vector (-y,x).
     *
     * @return a new instance of vector perpendicular to this
     */
    public Vector orthogonal() {
        return new Vector(-y, x);
    }

    private static String doubleToString(double d) {
        return String.format("%.3f", d);
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

        final Vector other = (Vector) obj;

        final double xAbs = Math.abs(x - other.x);
        final double yAbs = Math.abs(y - other.y);

        return !(xAbs > COMPARISON_TOLERANCE) && !(yAbs > COMPARISON_TOLERANCE);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "[x=" + doubleToString(x) + ", y=" + doubleToString(y) + "]";
    }
}
