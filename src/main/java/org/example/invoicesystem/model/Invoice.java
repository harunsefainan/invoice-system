package org.example.invoicesystem.model;

import java.time.LocalDate;

public class Invoice {
    private final double totalAmount;
    private final LocalDate date;

    public Invoice(double totalAmount, LocalDate date) {
        this.totalAmount = totalAmount;
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }
}
