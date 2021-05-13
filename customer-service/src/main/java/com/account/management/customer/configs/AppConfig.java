package com.account.management.customer.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

	private static final String API_MIME_TYPE = "application/json";
	private static final String USER_AGENT = "Spring 5 WebClient";

	@Value("${external-service.customer-url}")
	private String customerUrl;

	@Value("${external-service.transaction-url}")
	private String transactionUrl;

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public WebClient customerServiceWebClient(WebClient.Builder webClientBuilder) {
		return webClientBuilder
				.clone()
				.baseUrl(customerUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, API_MIME_TYPE)
				.defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.build();
	}

	@Bean
	public WebClient transactionServiceWebClient(WebClient.Builder webClientBuilder) {
		return webClientBuilder
				.clone()
				.baseUrl(transactionUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, API_MIME_TYPE)
				.build();
	}

}
