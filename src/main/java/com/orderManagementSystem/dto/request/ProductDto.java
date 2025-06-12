package com.orderManagementSystem.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    @NotEmpty(message = "The length of name should be between 3 to 15")
    @Size(min = 3, max=15)
    String name;

    @NotNull(message = "Provide margin sale price")
    BigDecimal price;

    @NotNull(message = "Enter the total stock available")
    Integer stock;
}
