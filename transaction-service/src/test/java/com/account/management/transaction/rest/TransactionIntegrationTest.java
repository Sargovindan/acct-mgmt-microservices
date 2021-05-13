package com.account.management.transaction.rest;

import com.account.management.transaction.dto.TransactionDto;
import com.account.management.transaction.repositories.TransactionRepository;
import com.account.management.transaction.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionIntegrationTest extends RestApiTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;


    @Test
    void shouldCheckAmountIsRequired() throws Exception {

        TransactionDto transactionDto = createDto();
        transactionDto.setAmount(null);
        String request = asJsonString(transactionDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status", is("error")));


    }

    @Test
    void shouldCheckAccountIdIsRequired() throws Exception {

        TransactionDto transactionDto = createDto();
        transactionDto.setAccountId(null);
        String request = asJsonString(transactionDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status", is("error")));


    }

    @Test
    void shouldNotCreateTransactionWithZeroAmount() throws Exception {

        TransactionDto transactionDto = createDto();
        transactionDto.setAmount(BigDecimal.ZERO);
        int count = transactionRepository.findAll().size();
        String request = asJsonString(transactionDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", is("success")));

        int newCount = transactionRepository.findAll().size();
        Assertions.assertEquals(count, newCount);

    }

    @Test
    void shouldCreateTransaction() throws Exception {

        TransactionDto transactionDto = createDto();
        int count = transactionRepository.findAll().size();
        String request = asJsonString(transactionDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", is("success")));

        int newCount = transactionRepository.findAll().size();
        Assertions.assertEquals(count+1, newCount);

    }

    private TransactionDto createDto() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.TEN);
        transactionDto.setAccountId(1L);

        return transactionDto;
    }
}
