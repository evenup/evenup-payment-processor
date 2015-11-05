package com.evenup.payment.processor;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Optional;

import com.evenup.payment.processor.crypto.AesDecryptFileUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import io.dropwizard.servlets.tasks.Task;

/**
 * A Dropwizard task that tells the process to begin writing to 
 * another file and decrypts the old file to the directory
 * specified in the configuration. 
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
public class CutNewFileTask extends Task {

    private final PaymentWriterProvider paymentWriterProvider;
    private final PaymentProcessorConfiguration config;

    @Inject
    public CutNewFileTask(PaymentWriterProvider paymentWriterProvider,
            PaymentProcessorConfiguration config) {
        super("new-file");
        this.paymentWriterProvider = paymentWriterProvider;
        this.config = config;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters,
            PrintWriter output) throws Exception {
        Optional<String> oldFile = paymentWriterProvider.cutFile();
        if (oldFile.isPresent() && config.getKeyFilePath().isPresent()) {
            Files.createDirectories(Paths.get(config.getDecryptedFileDestination()), new FileAttribute<?>[] {});
            new AesDecryptFileUtil().decrypt(config.getKeyFilePath().get(),
                    Paths.get(config.getFileDestination(),
                            oldFile.get()),
                    Paths.get(config.getDecryptedFileDestination(), oldFile.get()));
        } else {
            output.println("No file has been written to.");
        }
    }

}
