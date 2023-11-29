package com.example.jdbcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JdbcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcDemoApplication.class, args);
        System.out.println("Jdbc is running");
    }

}
