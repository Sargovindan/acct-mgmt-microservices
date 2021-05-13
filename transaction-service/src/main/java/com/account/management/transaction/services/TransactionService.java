package com.account.management.transaction.services;

import com.account.management.transaction.dto.TransactionDto;
import com.account.management.transaction.models.Transaction;
import com.account.management.transaction.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public TransactionDto createTransaction(TransactionDto transactionDto)
            throws IllegalArgumentException {
        log.info("Creating a transaction for account==== {} ", transactionDto.getAccountId());
        if(transactionDto.getAmount().intValue() != 0) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(transactionDto.getAccountId());
            transaction.setAmount(transactionDto.getAmount());

            transactionRepository.save(transaction);
            log.info("saved with id==== {}", transaction.getId());
        }
        return transactionDto;
    }

    public List<TransactionDto> findByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        List<TransactionDto> transactionDtos = new ArrayList<>();
        for(Transaction t: transactions) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setAmount(t.getAmount());
            transactionDto.setAccountId(t.getAccountId());
            transactionDto.setTransactionDate(t.getCreationDate());
            transactionDtos.add(transactionDto);
        }
        return transactionDtos;
    }
}
