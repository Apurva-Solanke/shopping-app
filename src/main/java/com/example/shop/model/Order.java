package com.example.shop.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record Order(long id, List<OrderItem> items, BigDecimal total, Instant createdAt) {
}
