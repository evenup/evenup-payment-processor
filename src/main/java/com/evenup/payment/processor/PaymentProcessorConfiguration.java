package com.evenup.payment.processor;

import javax.validation.constraints.NotNull;

import org.secnod.dropwizard.shiro.ShiroConfiguration;

import com.google.common.collect.ImmutableMap;

import io.dropwizard.Configuration;

public class PaymentProcessorConfiguration extends Configuration {
    
    private ShiroConfiguration shiro;
    
    @NotNull
    private ImmutableMap<String, String> csvMapping;
    
    @NotNull
    private String csvFilename;
    
    public ShiroConfiguration getShiro() {
        return shiro; 
    }

    public ImmutableMap<String, String> getCsvMapping() {
        return csvMapping;
    }

    public String getCsvFilename() {
        return csvFilename;
    }

}
