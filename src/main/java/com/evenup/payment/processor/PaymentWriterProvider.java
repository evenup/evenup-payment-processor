package com.evenup.payment.processor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.shiro.crypto.CipherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.encoder.CsvEncoder;
import org.supercsv.encoder.DefaultCsvEncoder;

import com.evenup.payment.processor.crypto.AesKeyManager;
import com.evenup.payment.processor.crypto.CryptoCsvEncoder;
import com.evenup.payment.processor.crypto.CryptoCsvEncoder.DIRECTION;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * A Guice provider that serves the current PaymentWriter.  This enables
 * the current to change at some point.  Correct usage of this requires
 * that the client synchronize on this object.  See {@link PaymentWriterResource}. 
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
@Singleton
public class PaymentWriterProvider implements Provider<PaymentWriter> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PaymentWriter current;
    private String currentFileName;
    
    private final Provider<PaymentProcessorConfiguration> configProvider;
    private final CipherService cipherService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-A");

    @Inject
    public PaymentWriterProvider(Provider<PaymentProcessorConfiguration> configProvider,
            CipherService cipherService) {
        this.configProvider = configProvider;
        this.cipherService = cipherService;
    }

    @Override
    public synchronized PaymentWriter get() {
        if (current == null) {
            createNewFile();
        }
        return current;
    }

    private Optional<String> createNewFile() {
        final PaymentProcessorConfiguration config = configProvider.get();
        try {
            final String newFilename = config.getCsvFilename().replace("${date}",
                    LocalDateTime.now().format(formatter));
            OutputStream os = new FileOutputStream(Paths
                    .get(config.getFileDestination(), newFilename).toFile(),
                    true);
            CsvEncoder encoder = new DefaultCsvEncoder();
            if (config.getKeyFilePath().isPresent()) {
                logger.info("Key file provided [{}]; file will be encrypted.",
                        config.getKeyFilePath().get());
                encoder = new CryptoCsvEncoder(encoder, cipherService,
                        AesKeyManager
                                .keyFromPath(config.getKeyFilePath().get()),
                        DIRECTION.ENCRYPTING);
            }
            current = new CsvPaymentWriter(new OutputStreamWriter(os, "UTF-8"),
                    config.getCsvMapping(), encoder);
            currentFileName = newFilename;
            return Optional.of(newFilename);
        } catch (IOException e) {
            logger.error(
                    "Unable to open file given in the config: {}.  Unable to start."
                            + config.getCsvFilename());
            return Optional.empty();
        }
    }

    public synchronized Optional<String> cutFile() throws IOException {
        if (current == null) {
            return Optional.empty();
        }
        current.close();
        final String oldFile = currentFileName;
        createNewFile();
        return Optional.ofNullable(oldFile);
    }
}
