package com.example.shop.model;

import java.math.BigDecimal;

public record Product(long id, String name, String description, BigDecimal price, String emoji) {
}
