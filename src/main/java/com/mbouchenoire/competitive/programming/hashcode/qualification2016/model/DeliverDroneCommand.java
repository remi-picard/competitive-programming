package com.mbouchenoire.competitive.programming.hashcode.qualification2016.model;

public class DeliverDroneCommand implements DroneCommand {

    private final Drone drone;
    private final Order order;
    private final ProductType productType;
    private final int productQuantity;

    public DeliverDroneCommand(Drone drone, Order order, ProductType productType, int productQuantity) {
        this.drone = drone;
        this.order = order;
        this.productType = productType;
        this.productQuantity = productQuantity;
    }

    @Override
    public String print() {
        return String.format("%d D %d %d %d", drone.getId(), order.getId(), productType.getId(), productQuantity);
    }

    @Override
    public String toString() {
        return print();
    }
}
