package com.account.management.accounts.rest.responses;

import com.account.management.accounts.dto.AccountDto;
import com.account.management.accounts.dto.CustomerDto;
import com.account.management.accounts.dto.TransactionDto;
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
