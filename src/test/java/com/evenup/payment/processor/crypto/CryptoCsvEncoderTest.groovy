
package com.evenup.payment.processor.crypto

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.util.ByteSource;
import org.supercsv.encoder.DefaultCsvEncoder;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.evenup.payment.processor.crypto.CryptoCsvEncoder.DIRECTION;

import spock.lang.Specification

class CryptoCsvEncoderTest extends Specification {

    def reverseBytes = { ByteSource.Util.bytes(new String(it).reverse().bytes)}
    
    def "encryption should be reflexive"() {
        setup:
        CipherService cipherService = Mock()
        
        cipherService.encrypt(_,_) >> { args ->
            reverseBytes(args[0])
        }
        cipherService.decrypt(_,_) >> { args ->
            reverseBytes(args[0])
        }
        
        
        StringWriter encryptingWriter = new StringWriter()
        StringWriter decryptingWriter = new StringWriter()
        def encryptor = new CryptoCsvEncoder(new DefaultCsvEncoder(), cipherService, "key".bytes, DIRECTION.ENCRYPTING)
        def decryptor = new CryptoCsvEncoder(new DefaultCsvEncoder(), cipherService, "key".bytes, DIRECTION.DECRYPTING)
        CsvListWriter csvEncryptingWriter = new CsvListWriter(
            encryptingWriter, 
            new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useEncoder(encryptor).build())
        CsvListWriter csvDecryptingWriter = new CsvListWriter(
            decryptingWriter, 
            new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useEncoder(decryptor).build())
        def origLine1 = ['ab', 'cd']
        def origLine2 = ['e,f', 'gh']
        when:
        csvEncryptingWriter.write(origLine1)
        csvEncryptingWriter.write(origLine2)
        csvEncryptingWriter.close()
        def encryptedLines = encryptingWriter.toString().readLines()
        encryptedLines.each {
            csvDecryptingWriter.write(it.split(','))
        }
        csvDecryptingWriter.close()
        
        then:
        def line1 = encryptedLines[0]
        line1 == "YmE=,ZGM="
//        encryptor.decrypt(line1.split(',')[0]) == 'ab'
//        encryptor.decrypt(line1.split(',')[1]) == 'cd'
        def decryptedLines = decryptingWriter.toString().readLines()
        decryptedLines.size() == 2
        decryptedLines[0].split(',') == origLine1
        
    }
}
