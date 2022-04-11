package com.example.restapp;

import org.springframework.http.ResponseEntity;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("There is no product corresponding to id = " + id);
    }
}
