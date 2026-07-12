package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // Real, testable business logic - not just a static "hello world" string
    @GetMapping("/api/discount")
    public double discount(@RequestParam double price, @RequestParam int quantity) {
        return PriceCalculator.applyBulkDiscount(price, quantity);
    }
}
