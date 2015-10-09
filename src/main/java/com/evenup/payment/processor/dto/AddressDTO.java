package com.evenup.payment.processor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {
    private String city;
    private String line1;
    private String line2;
    private String state;
    private String zip;

    public String getCity() {
        return city;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

}
