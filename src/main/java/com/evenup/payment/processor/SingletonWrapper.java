package com.evenup.payment.processor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Unfortunately, this is needed because Guice and Dropwizard act strange when it
 * comes to injecting the configuration (see {@link PaymentsModule}. 
 *
 */
@Singleton
public class SingletonWrapper<T> {
    
    private final T t;

    @Inject
    public SingletonWrapper(T t) {
        this.t = t;
    }
    
    public T getWriter() {
        return t;
    }
}