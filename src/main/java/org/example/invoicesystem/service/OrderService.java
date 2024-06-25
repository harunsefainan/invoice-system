// OrderService.java
package org.example.invoicesystem.service;

import org.example.invoicesystem.model.Customer;
import org.example.invoicesystem.model.Invoice;
import org.example.invoicesystem.model.Order;
import org.example.invoicesystem.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    public Order createOrder(Customer customer, List<Product> products) {
        Order order = new Order();
        products.forEach(order::addProduct);
        customer.addOrder(order);
        return order;
    }

    public Invoice generateInvoice(Order order) {
        double totalAmount = order.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
        Invoice invoice = new Invoice(totalAmount, LocalDate.now());
        order.setInvoice(invoice);
        return invoice;
    }

    public List<Invoice> getInvoicesForCustomer(Customer customer) {
        return customer.getOrders().stream()
                .map(Order::getInvoice)
                .collect(Collectors.toList());
    }

    public List<Invoice> getAllInvoices(List<Customer> customers) {
        return customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .map(Order::getInvoice)
                .collect(Collectors.toList());
    }

    public List<Invoice> getInvoicesAboveAmount(List<Customer> customers, double amount) {
        return getAllInvoices(customers).stream()
                .filter(invoice -> invoice.getTotalAmount() > amount)
                .collect(Collectors.toList());
    }

    public double getAverageInvoiceAmountAbove(List<Customer> customers, double amount) {
        return getInvoicesAboveAmount(customers, amount).stream()
                .mapToDouble(Invoice::getTotalAmount)
                .average()
                .orElse(0);
    }

    public List<String> getCustomerNamesWithInvoicesBelowAmount(List<Customer> customers, double amount) {
        return customers.stream()
                .filter(customer -> customer.getOrders().stream()
                        .map(Order::getInvoice)
                        .anyMatch(invoice -> invoice.getTotalAmount() < amount))
                .map(Customer::getName)
                .collect(Collectors.toList());
    }

    public double getTotalInvoiceAmountForMonth(List<Customer> customers, int month) {
        return getAllInvoices(customers).stream()
                .filter(invoice -> invoice.getDate().getMonthValue() == month)
                .mapToDouble(Invoice::getTotalAmount)
                .sum();
    }

    public List<String> getSectorsWithAverageInvoiceAmountBelow(List<Customer> customers, int month, double amount) {
        return customers.stream()
                .filter(customer -> {
                    double average = customer.getOrders().stream()
                            .map(Order::getInvoice)
                            .filter(invoice -> invoice.getDate().getMonthValue() == month)
                            .mapToDouble(Invoice::getTotalAmount)
                            .average()
                            .orElse(0);
                    return average < amount;
                })
                .map(Customer::getSector)
                .distinct()
                .collect(Collectors.toList());
    }
}
