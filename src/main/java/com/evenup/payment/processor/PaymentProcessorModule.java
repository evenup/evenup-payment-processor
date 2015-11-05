package com.evenup.payment.processor;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CipherService;

import com.google.inject.AbstractModule;

/**
 * Wires the object graph for the payment processor.
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
public class PaymentProcessorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CipherService.class).to(AesCipherService.class);
        bind(PaymentWriter.class).toProvider(PaymentWriterProvider.class);
    }

}
