package com.example.jdbcdemo.Service;

import com.example.jdbcdemo.Entity.Customer;

import java.util.List;

public interface CustomerService {
    // ~save function in jpa, update if exist
    Customer saveCustomer(Customer customer);

    // delete the customer with id
    void delCustomerById(Integer id);

    void delAll();

    Customer findOneById(Integer id);

    Customer findByName(String name);

    List<Customer> findByNameAndAge(String name, Integer age);

    Customer queryByName(String name);
}
