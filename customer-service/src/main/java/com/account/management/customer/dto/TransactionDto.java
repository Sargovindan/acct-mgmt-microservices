package com.account.management.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {

    @NotNull(message = "Please provide an amount")
    private BigDecimal amount;

    private Long accountId;
}
