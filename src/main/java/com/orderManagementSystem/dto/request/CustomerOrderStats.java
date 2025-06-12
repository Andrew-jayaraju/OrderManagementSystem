package com.orderManagementSystem.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerOrderStats {

    Integer customerId;

    String customerName;

    Long totalOrders;

}
