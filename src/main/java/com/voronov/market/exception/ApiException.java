package com.voronov.market.exception;

import lombok.Getter;

/**
 * @author Alexey Voronov on 19.07.2023
 */
public class ApiException extends RuntimeException {
    @Getter
    protected String errorCode;

    public ApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}