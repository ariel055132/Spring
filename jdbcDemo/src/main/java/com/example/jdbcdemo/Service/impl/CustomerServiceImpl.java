package com.example.jdbcdemo.Service.impl;

import com.example.jdbcdemo.DAO.CustomerDAO;
import com.example.jdbcdemo.Entity.Customer;
import com.example.jdbcdemo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setCreateBy("SYSTEM");
        return customerDAO.save(customer);
    }

    @Override
    public void delCustomerById(Integer id) {
        customerDAO.deleteById(Long.valueOf(id));
    }

    @Override
    public void delAll() {
        customerDAO.deleteAll();
    }

    @Override
    public Customer findOneById(Integer id) {
        return customerDAO.findById(Long.valueOf(id)).orElse(null);
    }


    @Override
    public Customer findByName(String name) {
        return customerDAO.findByName(name);
    }

    @Override
    public List<Customer> findByNameAndAge(String name, Integer age) {
        return customerDAO.findByNameAndAge(name, age);
    }

    @Override
    public Customer queryByName(String name) {
        return customerDAO.queryByName(name);
    }
}
