package com.orderManagementSystem.doa;

import com.orderManagementSystem.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(nativeQuery = true, value = "SELECT c.id, c.name, COUNT(o.id) as totalOrders FROM orders o JOIN customer c ON o.customer_id = c.id GROUP BY c.id, c.name ORDER BY totalOrders DESC")
    List<Object[]> findTop5Customers(Pageable pageable);

    default List<Object[]> findTop5Customers() {
        return findTop5Customers(PageRequest.of(0, 5));
    }

    @Query(nativeQuery = true, value = "SELECT c.id, c.name, COUNT(o.id) as totalOrdersPlaced FROM orders o JOIN customer c ON o.customer_id = c.id GROUP BY c.id, c.name")
    List<Object[]> countOrdersPerCustomer();

}
