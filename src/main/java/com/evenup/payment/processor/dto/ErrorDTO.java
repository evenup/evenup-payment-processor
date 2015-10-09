package com.evenup.payment.processor.dto;

/**
 * 
 * Copyright 2015 EvenUp, Inc.
 * <p>
 * THE  CODE IS  PROVIDED "AS  IS",  WITHOUT WARRANTY  OF ANY  KIND, EXPRESS  
 * OR IMPLIED,  INCLUDING BUT  NOT LIMITED  TO THE WARRANTIES  OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
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
