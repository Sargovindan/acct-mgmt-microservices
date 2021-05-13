package com.account.management.accounts.rest;

import com.account.management.accounts.dto.AccountDto;
import com.account.management.accounts.dto.CustomerDto;
import com.account.management.accounts.rest.responses.AccountEnquiryResponse;
import com.account.management.accounts.rest.responses.AppResponse;
import com.account.management.accounts.services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping({ "api/accounts" })
@Slf4j
public class AccountController extends BaseController{

    @Autowired
    private AccountService accountService;

    @GetMapping("/check")
    public AppResponse check()
            throws IllegalArgumentException {

        return getResponse(new AccountDto());
    }

    @PostMapping
    public AppResponse<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto)
            throws IllegalArgumentException, JsonProcessingException, NoSuchAlgorithmException {
        log.info("create account with customer id==== {}", accountDto.getCustomerId());

        return getResponse(accountService.createAccountWithTransaction(accountDto));
    }

    @GetMapping("/{customerId}")
    public AppResponse<AccountEnquiryResponse> getAccountDetailsWithTrxns(@PathVariable("customerId") String customerId)
            throws IllegalArgumentException, JsonProcessingException, NoSuchAlgorithmException {
        log.info("get account transactions with customer id==== {}", customerId);

        return getResponse(accountService.getAccountDetailsWithTransactions(customerId));
    }
}
