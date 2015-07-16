package com.evenup.payment.processor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Unfortunately, this is needed because Guice and Dropwizard act strangely when it
 * comes to injecting the configuration (see {@link PaymentProcessorModule}. 
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