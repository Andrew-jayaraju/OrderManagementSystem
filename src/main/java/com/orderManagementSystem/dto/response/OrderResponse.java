package com.orderManagementSystem.dto.response;

import com.orderManagementSystem.dto.request.CustomerDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    CustomerResponse customerResponse;

    List<ProductResponse> productResponses;

    BigDecimal totalAmount;

}
