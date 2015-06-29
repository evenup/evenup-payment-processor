package com.evenup.payment.processor

import com.evenup.payment.processor.dto.CreditCardDTO;
import com.evenup.payment.processor.dto.PaymentDTO;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Maps;

import spock.lang.Specification

/**
 * 
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
class CsvPaymentWriterTest extends Specification {
    

    def "dto goes in, csv comes out"() {
        setup:
        Writer writer = new StringWriter()
        def dto = new PaymentDTO(requestedPaymentDate: '2012-09-09', 
            creditCardInfo: new CreditCardDTO(number: '123412341234'))
        def im = ImmutableMap.of("Payment Date", "requestedPaymentDate", "Number", "creditCardInfo.number")
        PaymentWriter converter = new CsvPaymentWriter(writer, 
            im)
        
        when:
        converter.write(dto)
        
        then:
        def csv = writer.toString()
        'Payment Date,Number\r\n2012-09-09,123412341234\r\n' == csv
    }
    
}
