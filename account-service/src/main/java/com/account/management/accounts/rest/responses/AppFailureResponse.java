package com.account.management.accounts.rest.responses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppFailureResponse implements Serializable {

	private static final long serialVersionUID = -3826972210952312616L;

	private final String status = "error";

	private String message;

	private Object data;

}