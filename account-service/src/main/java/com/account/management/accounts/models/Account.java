package com.account.management.accounts.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Account extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String customerId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(unique = true, nullable = false)
    private String accountNumber;

}
