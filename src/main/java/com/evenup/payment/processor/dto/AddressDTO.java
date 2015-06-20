package com.evenup.payment.processor.dto;

/**
 * 
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
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
