package com.orderManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.orderManagementSystem.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(nullable = false)
    LocalDateTime orderDate = LocalDateTime.now();

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false)
    BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    Status status;

}
