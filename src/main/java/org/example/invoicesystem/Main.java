package org.example.invoicesystem;

import org.example.invoicesystem.model.Customer;
import org.example.invoicesystem.model.Product;
import org.example.invoicesystem.service.CustomerService;
import org.example.invoicesystem.service.OrderService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        OrderService orderService = new OrderService();

        // Müşteri ve ürünleri oluşturma
        Customer customer1 = new Customer("Alice", "Technology");
        Customer customer2 = new Customer("Bob", "Finance");
        Customer customer3 = new Customer("Charlie", "Retail");
        Customer customer4 = new Customer("Dave", "Technology");

        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);
        customerService.addCustomer(customer3);
        customerService.addCustomer(customer4);

        Product product1 = new Product("Laptop", 1500);
        Product product2 = new Product("Smartphone", 800);
        Product product3 = new Product("Tablet", 600);
        Product product4 = new Product("Monitor", 300);

        // Sipariş ve fatura oluşturma
        orderService.createOrder(customer1, List.of(product1, product2));
        orderService.createOrder(customer2, List.of(product3, product4));
        orderService.createOrder(customer3, List.of(product1, product4));
        orderService.createOrder(customer4, List.of(product2, product3));

        // Faturaları oluşturma
        customer1.getOrders().forEach(orderService::generateInvoice);
        customer2.getOrders().forEach(orderService::generateInvoice);
        customer3.getOrders().forEach(orderService::generateInvoice);
        customer4.getOrders().forEach(orderService::generateInvoice);

        // Tüm müşterileri listeleme
        System.out.println("Tüm Müşteriler:");
        customerService.getAllCustomers().forEach(customer -> System.out.println(customer.getName()));

        // İsmin içinde 'C' harfi olan müşterileri listeleme
        System.out.println("\nİsminde 'C' harfi olan Müşteriler:");
        customerService.getCustomersWithNameContainingC().forEach(customer -> System.out.println(customer.getName()));

        // Haziran ayında kayıt olan müşterilerin faturalarının toplam tutarını listeleme
        System.out.println("\nHaziran ayında kayıt olan müşterilerin faturalarının toplam tutarı:");
        double totalJuneInvoices = orderService.getTotalInvoiceAmountForMonth(customerService.getAllCustomers(), 6);
        System.out.println(totalJuneInvoices);

        // Sistemdeki tüm faturaları listeleme
        System.out.println("\nTüm Faturalar:");
        orderService.getAllInvoices(customerService.getAllCustomers()).forEach(invoice -> System.out.println(invoice.getTotalAmount()));

        // 1500 TL üstündeki faturaları listeleme
        System.out.println("\n1500 TL üstündeki Faturalar:");
        orderService.getInvoicesAboveAmount(customerService.getAllCustomers(), 1500).forEach(invoice -> System.out.println(invoice.getTotalAmount()));

        // 1500 TL üstündeki faturaların ortalamasını hesaplama
        System.out.println("\n1500 TL üstündeki Faturaların Ortalaması:");
        double averageAbove1500 = orderService.getAverageInvoiceAmountAbove(customerService.getAllCustomers(), 1500);
        System.out.println(averageAbove1500);

        // 500 TL altındaki faturalara sahip müşterilerin isimlerini listeleme
        System.out.println("\n500 TL altındaki faturalara sahip müşterilerin isimleri:");
        orderService.getCustomerNamesWithInvoicesBelowAmount(customerService.getAllCustomers(), 500).forEach(System.out::println);

        // Haziran ayı faturalarının ortalaması 750 TL altı olan firmaların hangi sektörde olduğunu listeleme
        System.out.println("\nHaziran ayı faturalarının ortalaması 750 TL altı olan firmaların sektörleri:");
        orderService.getSectorsWithAverageInvoiceAmountBelow(customerService.getAllCustomers(), 6, 750).forEach(System.out::println);
    }
}