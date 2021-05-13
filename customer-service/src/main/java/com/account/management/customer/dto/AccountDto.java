package com.account.management.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {

    @NotNull(message = "Customer id is required")
    private String customerId;

    @NotNull
    private BigDecimal initialCredit;
}
