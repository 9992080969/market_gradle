package com.voronov.market.exception;

/**
 * @author Alexey Voronov on 19.07.2023
 */
public class ApiException extends RuntimeException {
    protected String errorCode;

    public ApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}