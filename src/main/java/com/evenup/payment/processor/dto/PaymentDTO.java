package com.evenup.payment.processor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {

    private CreditCardDTO creditCardInfo;
    private String requestedPaymentDate;
    private Double remitAmount;
    
    public CreditCardDTO getCreditCardInfo() {
        return creditCardInfo;
    }
    
    public String getRequestedPaymentDate() {
        return requestedPaymentDate;
    }
    
    public Double getRemitAmount() {
        return remitAmount;
    }
    
}
