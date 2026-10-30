package com.shop.service;

import com.shop.entity.*;
import com.shop.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(
            ProductRepository productRepository,
            OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public CustomerOrder placeOrder(CustomerOrder order) {

        Product product = productRepository
            .findById(order.getProductId())
            .orElseThrow(() ->
                new RuntimeException("Product not found"));

        if (order.getQuantity() <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (product.getStock() < order.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        double total =
            product.getPrice() * order.getQuantity();

        product.setStock(
            product.getStock() - order.getQuantity());

        productRepository.save(product);

        order.setTotalAmount(total);
        order.setStatus("CONFIRMED");
        order.setOrderDate(LocalDateTime.now());

        return orderRepository.save(order);
    }
}