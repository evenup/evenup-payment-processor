
package com.evenup.payment.processor.crypto

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.util.ByteSource;
import org.supercsv.encoder.DefaultCsvEncoder;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import spock.lang.Specification

class EncryptingCsvEncoderTest extends Specification {

    def "should encrypt every field"() {
        setup:
        StringWriter writer = new StringWriter()
        CipherService cipherService = Mock()
        cipherService.encrypt(_,_) >> { args ->
            ByteSource.Util.bytes(new String(args[0]).reverse().bytes)
        }
        cipherService.decrypt(_,_) >> { args ->
            ByteSource.Util.bytes(new String(args[0]).reverse().bytes)
        }
        def encoder = new EncryptingCsvEncoder(new DefaultCsvEncoder(), cipherService, "key".bytes)
        CsvPreference pref = new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useEncoder(encoder).build()
        CsvListWriter csvWriter = new CsvListWriter(writer, pref)
        
        when:
        csvWriter.write(['ab', 'cd'])
        csvWriter.close()
        
        then:
        def str = writer.toString()
        str == "YmE=,ZGM=\r\n"
        encoder.decrypt(str.split(',')[0]) == 'ab'
        encoder.decrypt(str.split(',')[1]) == 'cd'
        
    }
}
