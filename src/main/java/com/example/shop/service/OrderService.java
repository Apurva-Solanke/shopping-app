package com.example.shop.service;

import com.example.shop.model.Order;
import com.example.shop.model.OrderItem;
import com.example.shop.model.OrderRequest;
import com.example.shop.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final ProductService productService;
    private final List<Order> orders = new CopyOnWriteArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1000);

    public OrderService(ProductService productService) {
        this.productService = productService;
    }

    public Order placeOrder(OrderRequest request) {
        if (request == null || request.items() == null || request.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderRequest.Line line : request.items()) {
            if (line.quantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be positive");
            }
            Product product = productService.findById(line.productId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Unknown product: " + line.productId()));

            items.add(new OrderItem(product.id(), product.name(), product.price(), line.quantity()));
            total = total.add(product.price().multiply(BigDecimal.valueOf(line.quantity())));
        }

        Order order = new Order(idSequence.incrementAndGet(), items, total, Instant.now());
        orders.add(order);
        return order;
    }

    public List<Order> findAll() {
        return List.copyOf(orders);
    }
}
