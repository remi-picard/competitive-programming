package com.mbouchenoire.competitive.programming.hashcode.qualification2016.impl;

import com.mbouchenoire.competitive.programming.common.model.geometry.Coord;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeAlgorithm;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeInput;
import com.mbouchenoire.competitive.programming.hashcode.qualification2016.model.*;

import java.util.*;

public class Solution implements HashCodeAlgorithm {

    @Override
    public List<String> run(HashCodeInput input) {
        final Scanner scanner = input.getScanner();
        final String[] firstLine = scanner.nextLine().split(" ");
        final int rowCount = Integer.parseInt(firstLine[0]);
        final int columnCount = Integer.parseInt(firstLine[1]);
        final int droneCount = Integer.parseInt(firstLine[2]);
        final int simulationDeadline = Integer.parseInt(firstLine[3]);
        final int droneMaximumLoad = Integer.parseInt(firstLine[4]);

        final List<Drone> drones = new ArrayList<>(droneCount);

        final String productTypesLine = scanner.nextLine();
        final int productTypeCount = Integer.parseInt(productTypesLine);
        final String[] productTypeWeights = scanner.nextLine().split(" ");

        final List<ProductType> productTypes = new ArrayList<>(productTypeCount);

        for (int i = 0; i < productTypeWeights.length; i++) {
            final String productTypeWeight = productTypeWeights[i];
            final int weight = Integer.parseInt(productTypeWeight);
            final ProductType productType = new ProductType(i, weight);
            productTypes.add(productType);
        }

        final String warehouseCountLine = scanner.nextLine();
        final int warehouseCount = Integer.parseInt(warehouseCountLine);

        final List<Warehouse> warehouses = new ArrayList<>(warehouseCount);

        for (int warehouseId = 0; warehouseId < warehouseCount; warehouseId++) {

            final String[] warehouseCoords = scanner.nextLine().split(" ");
            final int warehouseRow = Integer.parseInt(warehouseCoords[0]);
            final int warehouseCol = Integer.parseInt(warehouseCoords[1]);

            final Coord warehouseCoord = new Coord(warehouseCol, warehouseRow);

            if (warehouseId == 0) {
                for (int droneId = 0; droneId < droneCount; droneId++) {
                    final Drone drone = new Drone(droneId, warehouseCoord);
                    drones.add(drone);
                }
            }

            final String[] productLine = scanner.nextLine().split(" ");
            final List<Product> warehouseProducts = new ArrayList<>();

            for (int productTypeId = 0; productTypeId < productLine.length; productTypeId++) {
                final int productTypeQuantity = Integer.parseInt(productLine[productTypeId]);

                for (int k = 0; k < productTypeQuantity; k++) {
                    final ProductType type = findProductType(productTypeId, productTypes);
                    final Product product = new Product(type);
                    warehouseProducts.add(product);
                }
            }

            final Warehouse warehouse = new Warehouse(warehouseId, warehouseCoord, warehouseProducts);
            warehouses.add(warehouse);
        }

        final int orderCount = Integer.parseInt(scanner.nextLine());

        final List<Order> orders = new ArrayList<>(orderCount);

        for (int i = 0; i < orderCount; i++) {
            final String[] orderCoordLine = scanner.nextLine().split(" ");
            final int orderRow = Integer.parseInt(orderCoordLine[0]);
            final int orderCol = Integer.parseInt(orderCoordLine[1]);
            final Coord orderCoord = new Coord(orderCol, orderRow);

            final int orderedProductCount = Integer.parseInt(scanner.nextLine());

            final String[] orderedTypesLine = scanner.nextLine().split( " ");

            final List<Product> neededProducts = new ArrayList<>();

            for (int productTypeId = 0; productTypeId < orderedTypesLine.length; productTypeId++) {
                final int productTypeQuantity = Integer.parseInt(orderedTypesLine[productTypeId]);
                final ProductType type = findProductType(productTypeId, productTypes);

                for (int k = 0; k < productTypeQuantity; k++) {
                    final Product product = new Product(type);
                    neededProducts.add(product);
                }
            }

            final Order order = new Order(i, orderCoord, neededProducts);
            orders.add(order);
        }

        throw new UnsupportedOperationException("solution implemented");
    }

    private static ProductType findProductType(int id, List<ProductType> types) {
        return types.stream().filter(productType -> id == productType.getId()).findFirst().orElseThrow(IllegalStateException::new);
    }
}
