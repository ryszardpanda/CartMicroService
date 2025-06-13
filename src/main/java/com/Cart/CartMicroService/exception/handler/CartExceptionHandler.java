package com.Cart.CartMicroService.exception.handler;

import com.Cart.CartMicroService.exception.ErrorMessage;
import com.Cart.CartMicroService.exception.NoIdException;
import com.Cart.CartMicroService.exception.WrongTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartExceptionHandler {

    @ExceptionHandler({NoIdException.class})
    public ResponseEntity<ErrorMessage> handleNoIdException(
            NoIdException ex){
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler({WrongTypeException.class})
    public ResponseEntity<ErrorMessage> handleWrongTypeException(
            WrongTypeException ex){
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }
}
