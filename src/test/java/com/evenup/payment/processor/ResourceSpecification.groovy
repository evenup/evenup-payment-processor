package com.evenup.payment.processor

import io.dropwizard.testing.junit.ResourceTestRule

import org.junit.Rule

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
abstract class ResourceSpecification extends Specification {

    abstract Object getResource()

    @Rule ResourceTestRule resources = ResourceTestRule.builder()
        .addResource(getResource())
        .build();

}
