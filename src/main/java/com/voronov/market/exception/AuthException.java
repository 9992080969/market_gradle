package com.voronov.market.exception;

/**
 * @author Alexey Voronov on 19.07.2023
 */
public class AuthException extends ApiException{

    public AuthException(String message, String errorCode) {
        super(message, errorCode);
    }
}