package com.Cart.CartMicroService.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoIdException extends RuntimeException{
    private final HttpStatus httpStatus;

    public NoIdException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
