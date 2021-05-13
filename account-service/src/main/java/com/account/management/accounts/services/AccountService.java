package com.account.management.accounts.services;

import com.account.management.accounts.dto.AccountDto;
import com.account.management.accounts.dto.CustomerDto;
import com.account.management.accounts.dto.TransactionDto;
import com.account.management.accounts.exceptions.InterServiceException;
import com.account.management.accounts.exceptions.RecordNotFoundException;
import com.account.management.accounts.models.Account;
import com.account.management.accounts.repositories.AccountRepository;
import com.account.management.accounts.rest.responses.AccountEnquiryResponse;
import com.account.management.accounts.rest.responses.AccountResponse;
import com.account.management.accounts.rest.responses.AppResponse;
import com.account.management.accounts.utils.AppUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.management.openmbean.InvalidOpenTypeException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ExternalService externalService;

    @Autowired
    private ObjectMapper objectMapper;

    public AccountDto createAccountWithTransaction(AccountDto accountDto)
            throws IllegalArgumentException {
        log.info("Creating account for customer==== {} ", accountDto.getCustomerId());

        try{
            AppResponse customerDto = externalService.getCustomer(accountDto.getCustomerId());
            if(Objects.isNull(customerDto)) {
                throw new RecordNotFoundException(String.format("Customer with id %s does not exist", accountDto.getCustomerId()));
            }
            Optional<Account> accountOptional = getOptionalByCustomerId(accountDto.getCustomerId());
            Account account;
            if(accountOptional.isPresent()) {
                account = accountOptional.get();
            }else {
                account = createEntity(accountDto);
            }
            updateBalance(account, accountDto.getInitialCredit());
            accountRepository.save(account);
            log.info("saved with id==== {}", account.getId());
            if(accountDto.getInitialCredit().intValue() != 0){
                createTransactionEntry(accountDto, account.getId());
            }
        }catch (WebClientResponseException responseException) {
            throw new InterServiceException(responseException.getResponseBodyAsString(), responseException.getRawStatusCode());

        }catch (WebClientRequestException requestException) {
            throw new InterServiceException(requestException.getMessage(), 500);
        }

        return accountDto;

    }

    public AccountEnquiryResponse getAccountDetailsWithTransactions(String customerId){
        AccountEnquiryResponse accountEnquiryResponse = new AccountEnquiryResponse();
        try{
            AppResponse customerAppResponseDto = externalService.getCustomer(customerId);
            if(Objects.isNull(customerAppResponseDto)) {
                throw new RecordNotFoundException(String.format("Customer with id %s does not exist", customerId));
            }
            Optional<Account> accountOptional = getOptionalByCustomerId(customerId);
            if(!accountOptional.isPresent()) {
                throw new RecordNotFoundException(String.format("Customer with id %s does not have account", customerId));
            }

            CustomerDto customerDto = objectMapper.convertValue(customerAppResponseDto.getData(), CustomerDto.class);
            Account account = accountOptional.get();
            AppResponse transactionsAppResponseDto = externalService.getTransactionsByAccountId(account.getId());
            List<TransactionDto> transactionDtoList = objectMapper.convertValue(transactionsAppResponseDto.getData(), new TypeReference<List<TransactionDto>>() { });


            accountEnquiryResponse.setCustomer(customerDto);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccountNumber(account.getAccountNumber());
            accountResponse.setCustomerId(account.getCustomerId());
            accountResponse.setBalance(account.getBalance());
            accountEnquiryResponse.setAccount(accountResponse);
            accountEnquiryResponse.setTransactions(transactionDtoList);
        }catch (WebClientResponseException responseException) {
            throw new InterServiceException(responseException.getResponseBodyAsString(), responseException.getRawStatusCode());

        }catch (WebClientRequestException requestException) {
            throw new InterServiceException(requestException.getMessage(), 500);
        }

        return accountEnquiryResponse;
    }

    public Optional<Account> getOptionalByCustomerId(String customerId) {

        Optional<Account> account = accountRepository.findByCustomerId(customerId);

        return account;
    }

    public Account getByCustomerId(String customerId) {

        Account account = accountRepository.findByCustomerId(customerId).orElseThrow(
                () -> new RecordNotFoundException("Account not found - " + customerId)
        );

        return account;
    }

    private void createTransactionEntry(AccountDto accountDto, Long accountId) {
        externalService.createTransaction(accountDto, accountId);
    }

    private void updateBalance(Account account, BigDecimal initalCredit) {
        BigDecimal balance = initalCredit.add(account.getBalance());
        account.setBalance(balance);
    }

    private Account createEntity(AccountDto accountDto) throws NoSuchElementException {

        Account account = new Account();
        account.setCustomerId(accountDto.getCustomerId());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountNumber(AppUtils.generateAccountNumber());

        return account;
    }

}
