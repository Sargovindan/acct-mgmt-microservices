package com.account.management.accounts.rest.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse {

    private BigDecimal balance;

    private String customerId;

    private String accountNumber;
}
