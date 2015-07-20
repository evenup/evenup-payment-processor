package com.evenup.payment.processor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CipherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.encoder.CsvEncoder;
import org.supercsv.encoder.DefaultCsvEncoder;

import com.evenup.payment.processor.crypto.AesKeyManager;
import com.evenup.payment.processor.crypto.CryptoCsvEncoder;
import com.evenup.payment.processor.crypto.CryptoCsvEncoder.DIRECTION;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Wires the object graph for the payment processor.
 * <p>
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
public class PaymentProcessorModule extends AbstractModule {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void configure() {
        bind(CipherService.class).to(AesCipherService.class);
    }

    @Provides
    public PaymentWriter provideWriter(PaymentProcessorConfiguration config,
            CipherService cipherService, AesKeyManager keyManager)
            throws Exception {
        try {
            OutputStream os = new FileOutputStream(config.getCsvFilename(),
                    true);
            CsvEncoder encoder = new DefaultCsvEncoder();
            // TODO this is a little error-prone for a provider. catch some
            // exceptions, etc
            if (config.getKeyFilePath().isPresent()) {
                logger.info("Key file provided [{}]; file will be encrypted.",
                        config.getKeyFilePath().get());
                encoder = new CryptoCsvEncoder(
                        encoder, 
                        cipherService,
                        AesKeyManager.keyFromPath(config.getKeyFilePath().get()),
                        DIRECTION.ENCRYPTING);

            }
            return new CsvPaymentWriter(new OutputStreamWriter(os, "UTF-8"),
                    config.getCsvMapping(), encoder);
        } catch (IOException e) {
            logger.error("Unable to open file given in the config: {}.  Unable to start."
                    + config.getCsvFilename());
            throw e;
        }
    }

}
