package com.account.management.customer.rest;

import com.account.management.customer.rest.responses.AppFailureResponse;
import com.account.management.customer.rest.responses.AppResponse;

public class BaseController {

    public <T> AppResponse<T> getResponse(T data) {

        if (data == null) {
            return getFailResponse(data);
        }
        return new AppResponse<T>("success", data);
    }

    public <T> AppResponse<T> getFailResponse(T data) {
        return new AppResponse<>("fail", data);
    }

    public AppFailureResponse getErrorResponse(Object data, String message) {
        return new AppFailureResponse(message, data);
    }
}
