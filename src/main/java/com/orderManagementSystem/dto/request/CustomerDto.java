package com.orderManagementSystem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto {

    @NotEmpty(message = "The length of name should be between 3 to 15")
    @Size(min = 3, max=15)
    String name;

    @NotEmpty
    @Email(message = "Enter a valid email")
    String email;

    @NotEmpty
    @Size(min = 10, max = 10, message = "Enter mobile number without country code")
    String phone;

}
