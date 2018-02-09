package com.mbouchenoire.competitive.programming.hashcode.qualification2016.model;

import com.mbouchenoire.competitive.programming.common.model.geometry.Coord;

import java.util.*;

public class Order {

    private final int id;
    private final Coord coord;
    private final List<Product> neededProducts;

    public Order(int id, Coord coord, List<Product> neededProducts) {
        this.id = id;
        this.coord = coord;
        this.neededProducts = new ArrayList<>(neededProducts);
    }

    public int getId() {
        return id;
    }

    public Coord getCoord() {
        return coord;
    }

    public List<Product> getNeededProducts() {
        return Collections.unmodifiableList(neededProducts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", coord=" + coord +
                ", products=" + neededProducts.size() +
                '}';
    }
}
