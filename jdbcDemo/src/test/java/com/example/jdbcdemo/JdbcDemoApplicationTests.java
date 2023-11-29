package com.example.jdbcdemo;

import com.example.jdbcdemo.DAO.CustomerDAO;
import com.example.jdbcdemo.Entity.Customer;
import jakarta.persistence.EntityListeners;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

// runs with random port to avoid port conflict
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JdbcDemoApplicationTests {
    @Autowired
    private CustomerDAO customerDAO;

    /*
    @BeforeEach
    public void setUp() {R
        // 一開始直接Truncate Table，避免測試
        customerDAO.deleteAll();
        customerDAO.flush();
    }
     */

    @Test
    @DisplayName("Test findAll(), query all data.")
    public void test1() throws Exception {
        // Create 3 data
        //customerDAO.save(new Customer("John", 10, "SYSTEM"));
        //customerDAO.save(new Customer("Candy", 20, "SYSTEM"));
        //customerDAO.save(new Customer("Andrew", 30, "SYSTEM"));
        Assertions.assertEquals(4, customerDAO.findAll().size());
    }

    @Test
    @DisplayName("Test findByName(), query customer with the name John")
    public void test2() {
        Assertions.assertEquals(10, customerDAO.findByName("John").getAge().longValue());
    }

    @Test
    @DisplayName("Test queryByName(), query customer with the name Candy")
    public void test3() {
        Assertions.assertEquals(20, customerDAO.findByName("Candy").getAge().longValue());
    }

    //@Test
    //@DisplayName("Test findByNameAndAge(), ")

}
