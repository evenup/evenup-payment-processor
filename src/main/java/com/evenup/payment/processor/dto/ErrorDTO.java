package com.evenup.payment.processor.dto;

/**
 * 
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
public class ErrorDTO {

    private String message;
    
    public ErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
