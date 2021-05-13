package com.account.management.transaction.dto;

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

    @NotNull(message = "Please provide an account id")
    private Long accountId;

    private Date transactionDate;

}
