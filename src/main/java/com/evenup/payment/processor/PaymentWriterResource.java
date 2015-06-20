package com.evenup.payment.processor;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.evenup.payment.processor.dto.PaymentDTO;

/**
 * Writes payments to an encrypted file.
 * <p>
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
@Path("/payments")
public class PaymentWriterResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response addPayment(PaymentDTO paymentDTO) {
        
        // is there a location that makes sense here?
        return Response.created(null).build();
    }
}
