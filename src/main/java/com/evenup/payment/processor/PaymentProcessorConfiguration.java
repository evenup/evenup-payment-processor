package com.evenup.payment.processor;

import org.secnod.dropwizard.shiro.ShiroConfiguration;

import io.dropwizard.Configuration;

public class PaymentProcessorConfiguration extends Configuration {
    
    private ShiroConfiguration shiro;
    
    public ShiroConfiguration getShiro() {
        return shiro; 
    }

}
