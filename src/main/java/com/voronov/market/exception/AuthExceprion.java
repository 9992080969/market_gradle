package com.voronov.market.exception;

/**
 * @author Alexey Voronov on 19.07.2023
 */
public class AuthExceprion extends ApiException{

    public AuthExceprion(String message, String errorCode) {
        super(message, errorCode);
    }
}