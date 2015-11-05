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

    private Provider<PaymentWriter> writerProvider;

    @Inject
    public ProvideWriterHealthCheck(Provider<PaymentWriter> writerProvider) {
        this.writerProvider = writerProvider;
    }

    @Override
    protected Result check() throws Exception {
        try {
            PaymentWriter writer = writerProvider.get();
            if (writer == null)
                return Result.unhealthy("Unable to produce a PaymentWriter.");
        } catch (Exception e) {
            return Result.unhealthy("Unable to produce a PaymentWriter. " + e.getMessage());
        }
        return Result.healthy();
    }

    @Override
    public String getName() {
        return "writer-can-be-created";
    }

}
