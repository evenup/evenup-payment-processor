package com.evenup.payment.processor

import org.supercsv.encoder.DefaultCsvEncoder
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.evenup.payment.processor.dto.CreditCardDTO;
import com.evenup.payment.processor.dto.PaymentDTO;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Maps;

import spock.lang.Specification

/**
 * 
 * Copyright 2015 EvenUp, Inc.
 * <p>
 * THE  CODE IS  PROVIDED "AS  IS",  WITHOUT WARRANTY  OF ANY  KIND, EXPRESS  
 * OR IMPLIED,  INCLUDING BUT  NOT LIMITED  TO THE WARRANTIES  OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 * @author Kevin G. McManus
 *
 */
class CsvPaymentWriterTest extends Specification {
    private Writer writer

    def setup() {
        writer = new StringWriter()
    }

    def "dto goes in, csv comes out"() {
        setup:
        def dto = new PaymentDTO(requestedPaymentDate: '2012-09-09',
        creditCardInfo: new CreditCardDTO(number: '123412341234'))
        def im = ImmutableMap.of("Payment Date", "requestedPaymentDate", "Number", "creditCardInfo.number")
        PaymentWriter converter = new CsvPaymentWriter(writer,
                im, new DefaultCsvEncoder())

        when:
        converter.write(dto)

        then:
        def csv = writer.toString()
        'Payment Date,Number\r\n2012-09-09,123412341234\r\n' == csv
    }

    def "test payment plan"() {
        setup:
        def dto = new PaymentDTO(dayOfTheMonth: 15,
        creditCardInfo: new CreditCardDTO(number: '123412341234'))
        def im = ImmutableMap.of("Day of Month", "dayOfTheMonth", "Number", "creditCardInfo.number")
        PaymentWriter converter = new CsvPaymentWriter(writer,
                im, new DefaultCsvEncoder())

        when:
        converter.write(dto)

        then:
        def csv = writer.toString()
        'Day of Month,Number\r\n15,123412341234\r\n' == csv
    }
}
