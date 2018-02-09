package com.mbouchenoire.competitive.programming.hashcode.qualification2016.model;

import java.util.Objects;

public class ProductType {

    private final int id;
    private final int weight;

    public ProductType(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType that = (ProductType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", weight=" + weight +
                '}';
    }
}
