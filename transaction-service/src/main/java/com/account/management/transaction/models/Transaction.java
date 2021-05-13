package com.account.management.transaction.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class Transaction extends BaseEntity {

    private BigDecimal amount;

    @Column(name = "accountId_")
    private Long accountId;
}
