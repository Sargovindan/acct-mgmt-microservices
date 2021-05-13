package com.account.management.customer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@Setter
@Getter
public class Customer extends BaseEntity{

    @Column(name = "customer_id",unique = true)
    private String customerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;
}
