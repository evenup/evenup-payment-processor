package com.evenup.payment.processor;

import java.io.Closeable;
import java.io.IOException;

import com.evenup.payment.processor.dto.PaymentDTO;

/**
 * Responsible for writing PaymentDTO's.
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
public interface PaymentWriter extends Closeable {

    void write(PaymentDTO dto) throws IOException;
}
