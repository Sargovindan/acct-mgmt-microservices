package com.account.management.accounts.services;

import com.account.management.accounts.dto.AccountDto;
import com.account.management.accounts.dto.CustomerDto;
import com.account.management.accounts.dto.TransactionDto;
import com.account.management.accounts.rest.responses.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class ExternalService {

    @Autowired
    private WebClient customerServiceWebClient;

    @Autowired
    private WebClient transactionServiceWebClient;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public AppResponse getCustomer(String customerId)
            throws IllegalArgumentException {

        Mono<AppResponse> customer = customerServiceWebClient.get().uri("/customers/{customerId}", customerId)
                .retrieve().bodyToMono(AppResponse.class);
        return customer.block();

    }

    public void createTransaction(AccountDto accountDto, Long accountId) {
        TransactionDto t = new TransactionDto();
        t.setAmount(accountDto.getInitialCredit());
        t.setAccountId(accountId);

        Mono<AppResponse> response = transactionServiceWebClient.post()
                .uri("/transactions")
                .body(Mono.just(t), TransactionDto.class)
                .retrieve()
                .bodyToMono(AppResponse.class);
        response.block();
        //return response.block();
    }

    public AppResponse getTransactionsByAccountId(Long accountId) {

        Mono<AppResponse> response = transactionServiceWebClient.get()
                .uri("/transactions/{accountId}", accountId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AppResponse.class);
        return response.block();
    }
}
