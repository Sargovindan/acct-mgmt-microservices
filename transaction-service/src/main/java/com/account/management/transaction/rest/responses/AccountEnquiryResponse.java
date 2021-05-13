package com.account.management.transaction.rest.responses;

import com.account.management.transaction.dto.CustomerDto;
import com.account.management.transaction.dto.TransactionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountEnquiryResponse {

    private CustomerDto customer;

    private AccountResponse account;

    private List<TransactionDto> transactions;
}
