package com.example.restapp;

import org.springframework.http.ResponseEntity;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long id) {
        super("There is no order corresponding to id = " + id);
    }

}
