package com.evenup.payment.processor;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.evenup.payment.processor.dto.ErrorDTO;
import com.evenup.payment.processor.dto.PaymentDTO;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Writes payments to a (potentially) encrypted file.
 * <br><br>
 * Copyright 2015 EvenUp, Inc.
 * <p>
 * THE  CODE IS  PROVIDED "AS  IS",  WITHOUT WARRANTY  OF ANY  KIND, EXPRESS  
 * OR IMPLIED,  INCLUDING BUT  NOT LIMITED  TO THE WARRANTIES  OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 * @author Kevin G. McManus
 *
 */
@Path("/payments")
public class PaymentWriterResource {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(PaymentWriterResource.class);
    
    private final Provider<PaymentWriter> writerProvider;
    
    @Inject
    public PaymentWriterResource(Provider<PaymentWriter> writerProvider) {
        this.writerProvider = writerProvider;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response addPayment(PaymentDTO paymentDTO) {
        try {
            synchronized (writerProvider) {
                writerProvider.get().write(paymentDTO);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to write to file.", e);
            return Response.status(500).entity(new ErrorDTO("There was an error writing the payment.")).build();
        }
        // is there a location that makes sense here?
        return Response.created(null).build();
    }
}
