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
