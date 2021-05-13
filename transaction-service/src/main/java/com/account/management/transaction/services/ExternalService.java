package com.account.management.transaction.services;

import com.account.management.transaction.dto.CustomerDto;
import com.account.management.transaction.dto.TransactionDto;
import com.account.management.transaction.rest.responses.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ExternalService {

    @Autowired
    private WebClient customerServiceWebClient;


    public AppResponse getCustomer(String customerId)
            throws IllegalArgumentException {

        Mono<AppResponse> customer = customerServiceWebClient.get().uri("/customers/{customerId}", customerId)
                .retrieve().bodyToMono(AppResponse.class);
        return customer.block();

    }
}
