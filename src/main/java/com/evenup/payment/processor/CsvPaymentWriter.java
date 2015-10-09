package com.evenup.payment.processor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map.Entry;

import org.supercsv.encoder.CsvEncoder;
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.evenup.payment.processor.dto.PaymentDTO;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;

/**
 * Writes payment data to a CSV file.  The fields written are determined 
 * by the Map passed into the constructor.
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
public class CsvPaymentWriter implements PaymentWriter {
    
    private final CsvDozerBeanWriter beanWriter;
    
    /**
     * 
     * @param writer underlying writer, will be wrapped in a {@link BufferedWriter}.
     * @param headerToBeanField maps column headers to the fields in {@link PaymentDTO}.
     * @param encoder encoder used when writing out csv fields
     * @throws IOException
     */
    public CsvPaymentWriter(
            final Writer writer, 
            final ImmutableMap<String, String> headerToBeanField, CsvEncoder encoder) throws IOException {
        final String[] fieldMappings = new String[headerToBeanField.size()];
        final String[] headers = new String[headerToBeanField.size()];
        int i = 0;
        for (Entry<String, String> entry : headerToBeanField.entrySet()) {
            fieldMappings[i] = entry.getValue();
            headers[i++] = entry.getKey();
        }
        final CsvPreference prefs = new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useEncoder(encoder).build();
        beanWriter = new CsvDozerBeanWriter(writer, prefs);
        beanWriter.configureBeanMapping(PaymentDTO.class, fieldMappings);
        writeHeader(headers);
    }
    
    private synchronized void writeHeader(String[] headers) throws IOException {
        beanWriter.writeHeader(headers);
        beanWriter.flush();
    }
    
    public synchronized void write(PaymentDTO dto) throws IOException {
        beanWriter.write(dto);
        beanWriter.flush(); // TODO consider removing...
    }
    
    public synchronized void close() throws IOException {
        if (beanWriter != null)
            beanWriter.close(); 
    }

}
