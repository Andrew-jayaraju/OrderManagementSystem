package com.orderManagementSystem.service;

import com.orderManagementSystem.doa.CustomerRepository;
import com.orderManagementSystem.doa.OrderRepository;
import com.orderManagementSystem.doa.ProductRepository;
import com.orderManagementSystem.dto.mapper.CustomerMapper;
import com.orderManagementSystem.dto.mapper.ProductMapper;
import com.orderManagementSystem.dto.request.CustomerOrderStats;
import com.orderManagementSystem.dto.request.OrderDto;
import com.orderManagementSystem.dto.request.OrderItemDto;
import com.orderManagementSystem.dto.response.OrderResponse;
import com.orderManagementSystem.dto.response.ProductResponse;
import com.orderManagementSystem.entity.Customer;
import com.orderManagementSystem.entity.Order;
import com.orderManagementSystem.entity.OrderItem;
import com.orderManagementSystem.entity.Product;
import com.orderManagementSystem.entity.enums.Status;
import com.orderManagementSystem.exception.OrderNotFoundException;
import com.orderManagementSystem.exception.ProductNotFoundException;
import com.orderManagementSystem.exception.StockNotAvailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    public String createOrder(OrderDto dto) throws ProductNotFoundException, StockNotAvailableException {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemDto itemDto : dto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + itemDto.getProductId()));

            int availableStock = product.getStock();
            int requestedQty = itemDto.getQuantity();

            if (availableStock >= requestedQty) {
                // Enough stock
                product.setStock(availableStock - requestedQty);
            }else {
                // Not enough stock
                throw new StockNotAvailableException("Only " + availableStock + " units available for product: " + product.getName());
            }

            BigDecimal price = product.getPrice();
            BigDecimal subTotal = price.multiply(BigDecimal.valueOf(requestedQty));

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(requestedQty);
            orderItem.setProduct(product);
            orderItem.setPrice(price);
            orderItem.setSubTotal(subTotal);
            orderItem.setNote(itemDto.getNote());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(subTotal);

            if (product.getStock() > 0) {
                productRepository.save(product);
            }
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        return "Order has been created successfully";
    }

    public OrderResponse getOrder(Integer id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with order id " + id));

        OrderResponse orderResponse = new OrderResponse();
        Customer customer = order.getCustomer();
        orderResponse.setCustomerResponse(CustomerMapper.customerResponseMapper(customer));

        List<ProductResponse> list = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            list.add(ProductMapper.productResponseMapper(orderItem.getProduct()));
        }
        orderResponse.setProductResponses(list);
        orderResponse.setTotalAmount(order.getTotalAmount());
        return orderResponse;
    }


    public List<CustomerOrderStats> getTop5Customers() {
        List<Object[]> results = orderRepository.findTop5Customers();
        return results.stream()
                .map(obj -> new CustomerOrderStats(
                        (Integer) obj[0],
                        (String) obj[1],
                        (Long) obj[2]
                ))
                .collect(Collectors.toList());
    }

    public List<CustomerOrderStats> getTotalOrdersPerCustomer() {
        List<Object[]> results = orderRepository.countOrdersPerCustomer();
        return results.stream()
                .map(obj -> new CustomerOrderStats(
                        (Integer) obj[0],
                        (String) obj[1],
                        (Long) obj[2]
                ))
                .collect(Collectors.toList());
    }

    public ResponseEntity<List<OrderResponse>> fetchAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order : orderList){
            orderResponses.add(getOrder(order.getId()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderResponses);
    }

}
