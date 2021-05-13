package com.account.management.accounts.rest;

import com.account.management.accounts.dto.AccountDto;
import com.account.management.accounts.dto.CustomerDto;
import com.account.management.accounts.dto.TransactionDto;
import com.account.management.accounts.models.Account;
import com.account.management.accounts.repositories.AccountRepository;
import com.account.management.accounts.rest.responses.AppResponse;
import com.account.management.accounts.services.AccountService;;
import com.account.management.accounts.services.ExternalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountIntegrationTest extends RestApiTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private ExternalService externalService;

    @Test
    void shouldNotCreateAccountWithInvalidCustomerId() throws Exception {

        when(externalService.getCustomer(anyString())).thenReturn(null);
        AccountDto accountDto = createDto();
        accountDto.setCustomerId("xxxxxxxx");
        String request = asJsonString(accountDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.status", is("error")));

        int newCount = accountRepository.findAll().size();
        Assertions.assertEquals(0, newCount);

    }

    @Test
    void shouldCreateAccount() throws Exception {

        CustomerDto customerDto = createCustomerDto();
        AppResponse response = new AppResponse();
        response.setStatus("success");
        response.setData(customerDto);
        when(externalService.getCustomer(anyString())).thenReturn(response);
        int count = accountRepository.findAll().size();
        AccountDto accountDto = createDto();
        accountDto.setCustomerId(customerDto.getCustomerId());
        String request = asJsonString(accountDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")));

        int newCount = accountRepository.findAll().size();
        Assertions.assertEquals(count+1, newCount);

    }

    @Test
    void shouldGenerateAccountNumber() throws Exception {

        CustomerDto customerDto = createCustomerDto();
        AppResponse response = new AppResponse();
        response.setStatus("success");
        response.setData(customerDto);
        when(externalService.getCustomer(anyString())).thenReturn(response);
        AccountDto accountDto = createDto();
        accountDto.setCustomerId(customerDto.getCustomerId());
        String request = asJsonString(accountDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")));

        Optional<Account> account = accountRepository.findByCustomerId(accountDto.getCustomerId());
        Assertions.assertNotNull(account.get().getAccountNumber());
        Assertions.assertEquals(13, account.get().getAccountNumber().length());

    }

    @Test
    void shouldCreateTransactionWithNonZeroCredit() throws Exception {

        CustomerDto customerDto = createCustomerDto();
        AppResponse response = new AppResponse();
        response.setStatus("success");
        response.setData(customerDto);
        when(externalService.getCustomer(anyString())).thenReturn(response);
        AccountDto accountDto = createDto();
        accountDto.setInitialCredit(BigDecimal.TEN);
        accountDto.setCustomerId(customerDto.getCustomerId());
        String request = asJsonString(accountDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")));

        verify(externalService, times(1)).createTransaction(any(AccountDto.class), anyLong());

    }

    @Test
    void shouldNotCreateTransactionWithZeroCredit() throws Exception {

        CustomerDto customerDto = createCustomerDto();
        AppResponse response = new AppResponse();
        response.setStatus("success");
        response.setData(customerDto);
        when(externalService.getCustomer(anyString())).thenReturn(response);
        AccountDto accountDto = createDto();
        accountDto.setCustomerId(customerDto.getCustomerId());
        String request = asJsonString(accountDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")));

        verify(externalService, never()).createTransaction(any(AccountDto.class), anyLong());
    }

    @Test
    void shouldCheckCustomerIdIsRequired() throws Exception {

        AccountDto accountDto = new AccountDto();
        String request = asJsonString(accountDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/accounts").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status", is("error")));


    }

    @Test
    void shouldFetchAccountDetailsWithTransactionsGivenCustomerId() throws Exception {

        String accountNumber = "1234567890";
        int trxnCount = 5;
        CustomerDto customerDto = createCustomerDto();
        AppResponse customerDtoResponse = new AppResponse();
        customerDtoResponse.setStatus("success");
        customerDtoResponse.setData(customerDto);

        when(externalService.getCustomer(customerDto.getCustomerId())).thenReturn(customerDtoResponse);

        Account account = new Account();
        account.setBalance(BigDecimal.TEN);
        account.setAccountNumber(accountNumber);
        account.setCustomerId(customerDto.getCustomerId());
        Account savedAccount = accountRepository.save(account);

        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for (int i = 0; i < trxnCount; i++) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransactionDate(new Date());
            transactionDto.setAmount(BigDecimal.TEN);
            transactionDto.setAccountId(savedAccount.getId());
            transactionDtoList.add(transactionDto);
        }

        AppResponse transactionDtoResponse = new AppResponse();
        transactionDtoResponse.setStatus("success");
        transactionDtoResponse.setData(transactionDtoList);

        when(externalService.getTransactionsByAccountId(savedAccount.getId())).thenReturn(transactionDtoResponse);

        mvc.perform(MockMvcRequestBuilders.get("/api/accounts/{customerId}", customerDto.getCustomerId()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status", is("success")))
        .andExpect(jsonPath("$.data.customer.customerId", is(customerDto.getCustomerId())))
                .andExpect(jsonPath("$.data.account.accountNumber", is(account.getAccountNumber())))
                .andExpect(jsonPath("$.data.transactions", hasSize(trxnCount)))
                .andExpect(jsonPath("$.data.transactions[*].accountId").value(hasItem(account.getId().intValue())));


    }

    private AccountDto createDto() {
        AccountDto accountDto = new AccountDto();
        accountDto.setInitialCredit(BigDecimal.ZERO);
        return accountDto;
    }

    private CustomerDto createCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstname("John");
        customerDto.setSurname("Smith");
        customerDto.setCustomerId("1234567890123");

        return customerDto;
    }
}
