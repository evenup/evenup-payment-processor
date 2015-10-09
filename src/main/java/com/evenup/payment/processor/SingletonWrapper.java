package com.evenup.payment.processor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Unfortunately, this is needed because Guice and Dropwizard act strangely when it
 * comes to injecting the configuration (see {@link PaymentProcessorModule}. 
 * <br><br>
 * Current Copyright 2015 EvenUp, Inc.
 * <p>
 * THE  CODE IS  PROVIDED "AS  IS",  WITHOUT WARRANTY  OF ANY  KIND, EXPRESS  
 * OR IMPLIED,  INCLUDING BUT  NOT LIMITED  TO THE WARRANTIES  OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 */
@Singleton
public class SingletonWrapper<T> {
    
    private final T t;

    @Inject
    public SingletonWrapper(T t) {
        this.t = t;
    }
    
    public T getT() {
        return t;
    }
}