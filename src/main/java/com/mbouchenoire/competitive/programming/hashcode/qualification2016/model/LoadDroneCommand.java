package com.mbouchenoire.competitive.programming.hashcode.qualification2016.model;

public class LoadDroneCommand implements DroneCommand {

    private final Drone drone;
    private final Warehouse warehouse;
    private final ProductType productType;
    private final int productQuantity;

    public LoadDroneCommand(Drone drone, Warehouse warehouse, ProductType productType, int productQuantity) {
        this.drone = drone;
        this.warehouse = warehouse;
        this.productType = productType;
        this.productQuantity = productQuantity;
    }

    @Override
    public String print() {
        return String.format("%d L %d %d %d", drone.getId(), warehouse.getId(), productType.getId(), productQuantity);
    }

    @Override
    public String toString() {
        return print();
    }
}
