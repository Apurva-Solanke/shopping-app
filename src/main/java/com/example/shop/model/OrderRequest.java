package com.example.shop.model;

import java.util.List;

public record OrderRequest(List<Line> items) {
    public record Line(long productId, int quantity) {
    }
}
