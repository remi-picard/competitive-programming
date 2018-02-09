package com.mbouchenoire.competitive.programming.hashcode.qualification2016.model;

import java.util.Objects;
import java.util.UUID;

public class Product {

    private final UUID id;
    private final ProductType type;

    public Product(ProductType type) {
        this.id = UUID.randomUUID();
        this.type = type;
    }

    public ProductType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }
}
