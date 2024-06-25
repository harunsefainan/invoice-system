package org.example.invoicesystem.service;

import org.example.invoicesystem.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    private final List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Customer> getCustomersWithNameContainingC() {
        return customers.stream()
                .filter(customer -> customer.getName().contains("C"))
                .collect(Collectors.toList());
    }
}
