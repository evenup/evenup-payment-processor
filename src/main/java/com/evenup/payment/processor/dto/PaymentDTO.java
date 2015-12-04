package com.evenup.payment.processor.dto;

import javax.validation.constraints.NotNull;

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
public class PaymentDTO {

    private CreditCardDTO creditCardInfo;
    private String requestedPaymentDate;
    private Double remitAmount;
    private int dayOfTheMonth;
    private int numberOfPayments;
    @NotNull
    private String accountId;
    private String accountNumber;
    private String accountName;
    private Boolean testAccount;
    
    public CreditCardDTO getCreditCardInfo() {
        return creditCardInfo;
    }
    
    public String getRequestedPaymentDate() {
        return requestedPaymentDate;
    }
    
    public Double getRemitAmount() {
        return remitAmount;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public Boolean getTestAccount() {
        return testAccount;
    }

    public int getDayOfTheMonth() {
        return dayOfTheMonth;
    }

    public int getNumberOfPayments() {
        return numberOfPayments;
    }
}
