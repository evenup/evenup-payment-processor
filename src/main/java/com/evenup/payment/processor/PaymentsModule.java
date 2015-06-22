package com.evenup.payment.processor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.shiro.crypto.CipherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class PaymentsModule extends AbstractModule {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void configure() {

    }
    
    @Provides
    public PaymentWriter provideWriter(PaymentProcessorConfiguration config, CipherService cipherService)
            throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(config.getCsvFilename(), true);
//            new CipherOutputStream(fos, )
            return new CsvPaymentWriter(new OutputStreamWriter(fos), config.getCsvMapping());
        } catch (IOException e) {
            logger.error("Unable to open file given in the config: {}.  Unable to start."
                    + config.getCsvFilename());
            throw e;
        }
    }
}
