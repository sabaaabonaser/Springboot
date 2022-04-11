package com.example.restapp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class OrderAdvice {

    // מה שיהיה ב body יגיע ל response
    @ResponseBody
    // יעבוד רק בזמן שיהי שגיאה או תקלה
    @ExceptionHandler(OrderNotFoundException.class)
    // מה שיהיה ב response יהפוך ל status
    @ResponseStatus(HttpStatus.NOT_FOUND)

    String orderNotFoundHandler(OrderNotFoundException one){
        return one.getMessage();

    }
}