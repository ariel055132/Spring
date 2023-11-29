package com.example.jdbcdemo.Controller;

import com.example.jdbcdemo.Entity.Customer;
import com.example.jdbcdemo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/cus")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@RequestBody Customer customer) {
        customer = customerService.saveCustomer(customer);
        return "Successfully add user, the user id is " + customer.getId();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public Customer findCustomer(@PathVariable Integer id) {
        return customerService.findOneById(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delUser(@RequestParam("id") Integer id) {
        customerService.delCustomerById(id);
        return "Successfully delete user with id " + id;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public List<Customer> findUserByNameAndAge(@RequestBody Customer customer) {
        return customerService.findByNameAndAge(customer.getName(), customer.getAge());
    }
}
