package com.shop.controller;

import com.shop.entity.CustomerOrder;
import com.shop.repository.OrderRepository;
import com.shop.service.OrderService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(
            OrderService orderService,
            OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public CustomerOrder placeOrder(
            @RequestBody CustomerOrder order) {
        return orderService.placeOrder(order);
    }

    @GetMapping
    public List<CustomerOrder> getOrders() {
        return orderRepository.findAll();
    }
}