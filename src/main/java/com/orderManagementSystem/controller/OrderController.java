package com.orderManagementSystem.controller;

import com.orderManagementSystem.dto.request.CustomerOrderStats;
import com.orderManagementSystem.dto.request.OrderDto;
import com.orderManagementSystem.dto.response.OrderResponse;
import com.orderManagementSystem.entity.Order;
import com.orderManagementSystem.exception.OrderNotFoundException;
import com.orderManagementSystem.exception.ProductNotFoundException;
import com.orderManagementSystem.exception.StockNotAvailableException;
import com.orderManagementSystem.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderDto orderDto) throws ProductNotFoundException, StockNotAvailableException {
        String message = orderService.createOrder(orderDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(message);
    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") Integer id) throws OrderNotFoundException {
        OrderResponse order = orderService.getOrder(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(order);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        return orderService.fetchAllOrders();
    }

    @GetMapping("/reports/topCustomers")
    public List<CustomerOrderStats> getTop5Customers() {
        return orderService.getTop5Customers();
    }

    @GetMapping("/reports/ordersPerCustomer")
    public List<CustomerOrderStats> getTotalOrdersPerCustomer() {
        return orderService.getTotalOrdersPerCustomer();
    }

}
