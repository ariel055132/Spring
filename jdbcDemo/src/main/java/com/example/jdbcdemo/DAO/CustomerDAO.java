package com.example.jdbcdemo.DAO;

import com.example.jdbcdemo.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerDAO extends JpaRepository<Customer, Long> {
    // (使用自動化命名規則進行條件搜尋)
    Customer findByName(String name);

    List<Customer> findByNameAndAge(String name, Integer age);

    @Query(value = "select * from customer where name = ?1", nativeQuery = true)
    Customer queryByName(String name);
}
