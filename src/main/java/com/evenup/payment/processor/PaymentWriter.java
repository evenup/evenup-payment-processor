package com.evenup.payment.processor;

import java.io.Closeable;
import java.io.IOException;

import com.evenup.payment.processor.dto.PaymentDTO;

/**
 * Responsible for converting the PaymentDTO into a string.
 * <p>
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
public interface PaymentWriter extends Closeable {

    void write(PaymentDTO dto) throws IOException;
}
