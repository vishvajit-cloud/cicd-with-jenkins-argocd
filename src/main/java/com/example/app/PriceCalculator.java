package com.example.app;

public class PriceCalculator {

    private PriceCalculator() {
    }

    /**
     * Applies a bulk discount:
     *  - 10+ units  -> 10% off
     *  - 50+ units  -> 20% off
     *  - otherwise  -> no discount
     */
    public static double applyBulkDiscount(double price, int quantity) {
        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity cannot be negative");
        }

        double total = price * quantity;

        if (quantity >= 50) {
            return total * 0.80;
        } else if (quantity >= 10) {
            return total * 0.90;
        }
        return total;
    }
}
