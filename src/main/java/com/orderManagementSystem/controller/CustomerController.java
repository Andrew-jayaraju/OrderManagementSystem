package com.orderManagementSystem.controller;

import com.orderManagementSystem.dto.request.CustomerDto;
import com.orderManagementSystem.dto.response.CustomerResponse;
import com.orderManagementSystem.exception.UserAlreadyExistException;
import com.orderManagementSystem.exception.UserNotFoundException;
import com.orderManagementSystem.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody CustomerDto customerDto) throws UserAlreadyExistException {
        String message = customerService.registerCustomer(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(message);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerResponse> fetchCustomer(
            @RequestParam
            @Pattern(regexp = "^[0-9]{10}$", message = "Enter 10 digit mobile number")
            String phone) throws UserNotFoundException {

        CustomerResponse customer = customerService.fetchCustomer(phone);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<CustomerResponse>> fetchAllCustomer(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.fetchAllCustomers());
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(
            @Valid @RequestBody CustomerDto customerDto,
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "Enter 10 digit mobile number") String phone
    ) throws UserNotFoundException {
        String message = customerService.updateCustomer(customerDto, phone);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(message);
    }

}
