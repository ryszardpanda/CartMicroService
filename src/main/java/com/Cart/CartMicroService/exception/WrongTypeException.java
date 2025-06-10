package com.Cart.CartMicroService.exception;

import org.springframework.http.HttpStatus;

public class WrongTypeException extends RuntimeException{
    private HttpStatus httpStatus;

    public WrongTypeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
