package com.orderManagementSystem.doa;

import com.orderManagementSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByPhone(String phone);

}
