package com.evenup.payment.processor

import javax.ws.rs.core.MediaType;

import io.dropwizard.testing.junit.ResourceTestRule;

import com.sun.jersey.api.client.ClientResponse;

class PaymentWriterResourceTest extends ResourceSpecification {

    @Override
    public Object getResource() {
        return new PaymentWriterResource();
    }

    def "successfully write a payment"() {
        setup:
        def jsonIn = '''
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
        
        when:
        ClientResponse response = addPayment(jsonIn)
        
        then:
        response.status == 201
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
