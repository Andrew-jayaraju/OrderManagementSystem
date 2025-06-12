package com.orderManagementSystem.service;

import com.orderManagementSystem.doa.CustomerRepository;
import com.orderManagementSystem.dto.request.CustomerDto;
import com.orderManagementSystem.dto.mapper.CustomerMapper;
import com.orderManagementSystem.dto.response.CustomerResponse;
import com.orderManagementSystem.entity.Customer;
import com.orderManagementSystem.exception.UserAlreadyExistException;
import com.orderManagementSystem.exception.UserNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public String registerCustomer(CustomerDto customerDto) throws UserAlreadyExistException {
        Customer customer = customerRepository.findByPhone(customerDto.getPhone());
        if(customer != null){
            throw new UserAlreadyExistException("User already exist, try to login");
        }
        Customer saveCustomer = CustomerMapper.customerDtoMapper(customerDto);
        customerRepository.save(saveCustomer);
        return "Customer saved successfully";
    }

    public CustomerResponse fetchCustomer(String phone) throws UserNotFoundException {
        Customer customer = customerRepository.findByPhone(phone);
        if(customer == null){
            throw new UserNotFoundException("User with given number does not exist");
        }
        return CustomerMapper.customerResponseMapper(customer);
    }

    public String updateCustomer(CustomerDto customerDto, String phone) throws UserNotFoundException {
        Customer customer = customerRepository.findByPhone(phone);
        if(customer == null){
            throw new UserNotFoundException("User with given number does not exist");
        }
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(phone);
        customerRepository.save(customer);
        return "Customer updated successfully";
    }

    public List<CustomerResponse> fetchAllCustomers() {
        List<CustomerResponse> list = new ArrayList<>();
        for(Customer customer : customerRepository.findAll()){
            list.add(CustomerMapper.customerResponseMapper(customer));
        }
        return list;
    }
}
