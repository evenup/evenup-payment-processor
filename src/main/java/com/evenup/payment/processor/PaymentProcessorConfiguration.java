package com.evenup.payment.processor;

import javax.validation.constraints.NotNull;

import org.secnod.dropwizard.shiro.ShiroConfiguration;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import io.dropwizard.Configuration;

/**
 * Holds all config for the application.  Dropwizard populates
 * this from the YAML file given when starting up.
 * <p>
 * Copyright 2015 EvenUp, Inc.
 * <br><br>
 * THE  CODE IS  PROVIDED "AS  IS",  WITHOUT WARRANTY  OF ANY  KIND, EXPRESS  
 * OR IMPLIED,  INCLUDING BUT  NOT LIMITED  TO THE WARRANTIES  OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 * @author Kevin G. McManus
 *
 */
public class PaymentProcessorConfiguration extends Configuration {
    
    private ShiroConfiguration shiro;
    
    @NotNull
    private ImmutableMap<String, String> csvMapping;
    
    @NotNull
    private String csvFilename;
    
    private String keyFilePath;
    
    public ShiroConfiguration getShiro() {
        return shiro; 
    }

    public ImmutableMap<String, String> getCsvMapping() {
        return csvMapping;
    }

    public String getCsvFilename() {
        return csvFilename;
    }

    public Optional<String> getKeyFilePath() {
        return Optional.fromNullable(keyFilePath);
    }

}
