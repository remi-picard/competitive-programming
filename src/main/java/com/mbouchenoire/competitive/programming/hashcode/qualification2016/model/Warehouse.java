package com.mbouchenoire.competitive.programming.hashcode.qualification2016.model;

import com.mbouchenoire.competitive.programming.common.model.geometry.Coord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Warehouse {

    private final int id;
    private final Coord coord;
    private final List<Product> products;

    public Warehouse(int id, Coord coord, List<Product> products) {
        this.id = id;
        this.coord = coord;
        this.products = new ArrayList<>(products);
    }

    public int getId() {
        return id;
    }

    public Coord getCoord() {
        return coord;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return id == warehouse.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", coord=" + coord +
                ", products=" + products.size() +
                '}';
    }
}
