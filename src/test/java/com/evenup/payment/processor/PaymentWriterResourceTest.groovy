package com.evenup.payment.processor

import javax.ws.rs.core.MediaType;

import io.dropwizard.testing.junit.ResourceTestRule;

import com.evenup.payment.processor.dto.PaymentDTO
import com.google.inject.Provider
import com.sun.jersey.api.client.ClientResponse;

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
class PaymentWriterResourceTest extends ResourceSpecification {
    
    private String jsonIn = '''
{
    "creditCardInfo": {
        "number": "5555555555554444",
        "type": "MC",
        "expirationMonth": "12",
        "expirationYear": "2016",
        "accountHolderName": "C. C. Holder",
        "accountAddress": {
            "line1": "123 Main St.",
            "line2": "",
            "city": "Atlanta",
            "state": "GA",
            "zip": "80128"
        }
    },
    "requestedPaymentDate": "2015-04-11",
    "remitAmount": 25.00
}
        '''

    PaymentWriter writer
    
    @Override
    public Object getResource() {
        writer = Mock()
        return new PaymentWriterResource(new Provider<PaymentWriter>() {
            PaymentWriter get() {return writer}
        });
    }

    def "successfully write a payment"() {
        setup:
        PaymentDTO dtoCapture
        
        when:
        ClientResponse response = addPayment(jsonIn)
        
        then:
        1 * writer.write(_) >> { args ->
            dtoCapture = args[0]
        }
        dtoCapture.creditCardInfo.number == '5555555555554444'
        response.status == 201
    }
    
    def "IOException should cause a 500"() {
        setup:
        writer.write(_) >> {throw new IOException()}
        
        when:
        ClientResponse response = addPayment(jsonIn)
        
        then:
        response.status == 500
    }
    
    private ClientResponse addPayment(json) {
        def resourcesClient = resources.client()
        ClientResponse response = resourcesClient
                .resource('/payments')
                .entity(json, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class)
        return response
    }
}
