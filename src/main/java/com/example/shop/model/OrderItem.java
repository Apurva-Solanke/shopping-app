package com.example.shop.model;

import java.math.BigDecimal;

public record OrderItem(long productId, String name, BigDecimal unitPrice, int quantity) {
}
