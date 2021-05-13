package com.account.management.transaction.rest;

import com.account.management.transaction.dto.TransactionDto;
import com.account.management.transaction.rest.responses.AppResponse;
import com.account.management.transaction.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({ "api/transactions" })
@Slf4j
public class TransactionController extends BaseController{

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/check")
    public AppResponse check()
            throws IllegalArgumentException {

        return getResponse(new TransactionDto());
    }

    @PostMapping
    public AppResponse<TransactionDto> createTransaction(@Valid @RequestBody TransactionDto transactionDto)
            throws IllegalArgumentException {
        log.info("create transaction with account id==== {}", transactionDto.getAccountId());

        return getResponse(transactionService.createTransaction(transactionDto));
    }

    @GetMapping("/{accountId}")
    public AppResponse<List<TransactionDto>> getTransactionsByAccountId(@PathVariable Long accountId)
            throws IllegalArgumentException {
        log.info("get transactions for account id==== {}", accountId);

        return getResponse(transactionService.findByAccountId(accountId));
    }
}
