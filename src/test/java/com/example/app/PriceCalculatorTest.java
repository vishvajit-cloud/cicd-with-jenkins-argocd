package com.example.app;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceCalculatorTest {

    @Test
    void noDiscount_whenQuantityBelowTen() {
        double result = PriceCalculator.applyBulkDiscount(10.0, 5);
        assertThat(result).isEqualTo(50.0);
    }

    @Test
    void tenPercentDiscount_whenQuantityAtLeastTen() {
        double result = PriceCalculator.applyBulkDiscount(10.0, 10);
        assertThat(result).isEqualTo(90.0);
    }

    @Test
    void twentyPercentDiscount_whenQuantityAtLeastFifty() {
        double result = PriceCalculator.applyBulkDiscount(10.0, 50);
        assertThat(result).isEqualTo(400.0);
    }

    @Test
    void throwsException_whenPriceNegative() {
        assertThatThrownBy(() -> PriceCalculator.applyBulkDiscount(-1.0, 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void throwsException_whenQuantityNegative() {
        assertThatThrownBy(() -> PriceCalculator.applyBulkDiscount(10.0, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
