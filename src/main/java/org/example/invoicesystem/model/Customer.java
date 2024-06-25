package org.example.invoicesystem.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private final String sector;
    private final List<Order> orders = new ArrayList<>();

    public Customer(String name, String sector) {
        this.name = name;
        this.sector = sector;
    }

    public String getName() {
        return name;
    }

    public String getSector() {
        return sector;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
