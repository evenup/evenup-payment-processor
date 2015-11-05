package com.evenup.payment.processor

import java.nio.file.Path
import java.nio.file.Paths;

import org.apache.shiro.crypto.AesCipherService

import com.google.common.collect.ImmutableMap;
import com.google.inject.Provider;

import spock.lang.Specification

class PaymentWriterProviderTest extends Specification {
    private AesCipherService cipherService
    private PaymentProcessorConfiguration config
    private PaymentWriterProvider writerProvider

    def setup() {
        config = Mock()
        def configP = new Provider<PaymentProcessorConfiguration>() {
            PaymentProcessorConfiguration get() {config}
        }
        cipherService = Mock()
        writerProvider = new PaymentWriterProvider(configP, cipherService)    
    }
    
    def "first time should create a new writer"(){
        setup:
        File t = File.createTempFile('aaa','aaa')
        t.deleteOnExit()
        Path path = Paths.get(t.absolutePath)
        config.getKeyFilePath() >> Optional.empty()
        config.getCsvMapping() >> ImmutableMap.of("Payment Date", "requestedPaymentDate", "Number", "creditCardInfo.number")
        
        when:
        writerProvider.get()
        
        then:
        1 * config.getCsvFilename() >> path.getFileName()
        1 * config.getFileDestination() >> path.getParent().toString()
        writerProvider.currentFileName == path.getFileName().toString()
    }
    
}
