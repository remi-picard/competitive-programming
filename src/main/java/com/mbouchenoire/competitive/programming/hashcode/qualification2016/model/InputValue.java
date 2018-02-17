package com.mbouchenoire.competitive.programming.hashcode.qualification2016.model;

import java.util.Collections;
import java.util.List;

public class InputValue {

    public List<Order> orders;
    public List<Warehouse> warehouses;
    public List<Drone> drones;

    public InputValue(List<Order> orders, List<Warehouse> warehouses, List<Drone> drones) {
        this.orders = Collections.unmodifiableList(orders);
        this.warehouses = Collections.unmodifiableList(warehouses);
        this.drones = Collections.unmodifiableList(drones);
    }
}
