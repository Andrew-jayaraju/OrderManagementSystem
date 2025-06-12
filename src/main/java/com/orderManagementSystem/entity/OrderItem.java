package com.orderManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false)
    Integer quantity;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    BigDecimal subTotal;

    @Column()
    String note;

}
