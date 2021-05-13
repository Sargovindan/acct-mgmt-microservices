package com.account.management.accounts.exceptions;

import lombok.Getter;

@Getter
public class InterServiceException extends RuntimeException{

    private int statusCode;

    public InterServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
