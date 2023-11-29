package com.example.jdbcdemo.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "customer", schema = "practiceJPA")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Customer implements Serializable {
    // Primary Key
    @Id
    // Primary Key is maintained by database (AUTO_INCREMENT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    // Primary Key is maintained by database (AUTO_INCREMENT)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "create_by")
    private String createBy;

    @CreatedDate
    @Column(name = "create_dt")
    // Create the timestamp automatically
    private Date createDt;

    @Column(name = "modify_by")
    private String modifyBy;

    @LastModifiedDate
    @Column(name = "modify_dt")
    private Date modifyDt;

    public Customer() {

    }

    public Customer(String name, Integer age, String createBy) {
        this.name = name;
        this.age = age;
        this.createBy = createBy;
    }

}
