package com.evenup.payment.processor.dto;

/**
 * 
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
public class CreditCardDTO {
    private String accountHolderName;
    private String number;
    private String type;
    private String expirationMonth;
    private String expirationYear;
    private String securityCode;
    private AddressDTO accountAddress;

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public AddressDTO getAccountAddress() {
        return accountAddress;
    }
}
