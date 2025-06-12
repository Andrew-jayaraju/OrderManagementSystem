package com.orderManagementSystem.dto.request;

import com.orderManagementSystem.entity.Customer;
import com.orderManagementSystem.entity.OrderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    Integer customerId;

    List<OrderItemDto> items;

}
