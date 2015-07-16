package com.evenup.payment.processor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;

/**
 * This is to make sure create the writer.  Otherwise, we would find out at our first request.
 * <p>
 * Current Copyright 2014 EvenUp, Inc.
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
