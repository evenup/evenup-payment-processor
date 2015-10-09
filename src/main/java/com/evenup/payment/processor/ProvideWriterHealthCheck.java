package com.evenup.payment.processor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;

/**
 * This is to make sure create the writer.  Otherwise, we would find out at our first request.
 * <br><br>
 * Current Copyright 2015 EvenUp, Inc.
 * <p>
 * THE  CODE IS  PROVIDED "AS  IS",  WITHOUT WARRANTY  OF ANY  KIND, EXPRESS  
 * OR IMPLIED,  INCLUDING BUT  NOT LIMITED  TO THE WARRANTIES  OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 * @author Kevin G. McManus
 *
 */
public class ProvideWriterHealthCheck extends InjectableHealthCheck {

    private Provider<SingletonWrapper<PaymentWriter>> converter;

    @Inject
    public ProvideWriterHealthCheck(Provider<SingletonWrapper<PaymentWriter>> converter) {
        this.converter = converter;
    }

    @Override
    protected Result check() throws Exception {
        try {
            SingletonWrapper<PaymentWriter> singletonWrapper = converter.get();
            @SuppressWarnings("unused")
            PaymentWriter writer = singletonWrapper.getT();
        } catch (Throwable t) {
            return Result.unhealthy("Unable to produce a PaymentWriter. " + t.getMessage());
        }
        return Result.healthy();
    }

    @Override
    public String getName() {
        return "writer-can-be-created";
    }

}
