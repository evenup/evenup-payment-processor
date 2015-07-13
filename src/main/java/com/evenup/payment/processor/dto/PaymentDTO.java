package com.evenup.payment.processor.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {

    private CreditCardDTO creditCardInfo;
    private String requestedPaymentDate;
    private Double remitAmount;
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
}
