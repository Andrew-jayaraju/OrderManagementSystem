package com.orderManagementSystem.dto.mapper;

import com.orderManagementSystem.dto.request.CustomerDto;
import com.orderManagementSystem.dto.response.CustomerResponse;
import com.orderManagementSystem.entity.Customer;

public class CustomerMapper {

    public static Customer customerDtoMapper(CustomerDto customerDto){
        return Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .build();
    }

    public static CustomerResponse customerResponseMapper(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }

}
