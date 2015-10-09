package com.evenup.payment.processor;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.secnod.dropwizard.shiro.ShiroBundle;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

import com.hubspot.dropwizard.guice.GuiceBundle;

/**
 * Entry point for the payment processor
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
public class PaymentProcessorApplication extends
        Application<PaymentProcessorConfiguration> {

    private final ShiroBundle<PaymentProcessorConfiguration> shiroBundle = new ShiroBundle<PaymentProcessorConfiguration>() {

        @Override
        protected ShiroConfiguration narrow(
                PaymentProcessorConfiguration configuration) {
            return configuration.getShiro();
        }

    };
    private GuiceBundle<PaymentProcessorConfiguration> guiceBundle;

    @Override
    public void initialize(Bootstrap<PaymentProcessorConfiguration> bootstrap) {
        bootstrap.addBundle(shiroBundle);
        guiceBundle = GuiceBundle.<PaymentProcessorConfiguration> newBuilder()
                .addModule(new PaymentProcessorModule())
                .setConfigClass(PaymentProcessorConfiguration.class)
                .enableAutoConfig(getClass().getPackage().getName()).build();
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(PaymentProcessorConfiguration configuration,
            Environment environment) throws Exception {

    }
    
    public static void main(String[] args) throws Exception {
        new PaymentProcessorApplication().run(args);
    }

}
