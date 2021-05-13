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
public class AppResponse<T> implements Serializable {

	private static final long serialVersionUID = -3826972210952312616L;

	private String status;

	private T data;
}
