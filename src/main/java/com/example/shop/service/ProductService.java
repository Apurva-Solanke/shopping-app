package com.example.shop.service;

import com.example.shop.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    // In-memory catalog: no database needed, keeps the container simple.
    private final List<Product> products = List.of(
            new Product(1, "Wireless Mouse", "Ergonomic 2.4GHz wireless mouse", new BigDecimal("799"), "🖱️"),
            new Product(2, "Mechanical Keyboard", "Blue switch, backlit keyboard", new BigDecimal("2999"), "⌨️"),
            new Product(3, "USB-C Hub", "7-in-1 hub with HDMI and card reader", new BigDecimal("1799"), "🔌"),
            new Product(4, "Laptop Stand", "Adjustable aluminium stand", new BigDecimal("1299"), "💻"),
            new Product(5, "Noise Cancelling Headphones", "Over-ear Bluetooth headphones", new BigDecimal("4999"), "🎧"),
            new Product(6, "Webcam 1080p", "Full HD webcam with microphone", new BigDecimal("2199"), "📷"),
            new Product(7, "Water Bottle", "1 litre insulated steel bottle", new BigDecimal("599"), "🍶"),
            new Product(8, "Backpack", "Water-resistant 25L laptop backpack", new BigDecimal("1899"), "🎒")
    );

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(long id) {
        return products.stream().filter(p -> p.id() == id).findFirst();
    }
}
