package com.evenup.payment.processor;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.secnod.dropwizard.shiro.ShiroBundle;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

import com.hubspot.dropwizard.guice.GuiceBundle;

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
        // TODO Auto-generated method stub

    }
    
    public static void main(String[] args) throws Exception {
        new PaymentProcessorApplication().run(args);
    }

}
