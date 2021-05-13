package com.account.management.accounts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {

    @NotNull(message = "Please provide an amount")
    private BigDecimal amount;

    private Long accountId;

    private Date transactionDate;
}
