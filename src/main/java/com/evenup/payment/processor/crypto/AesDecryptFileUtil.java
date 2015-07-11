package com.evenup.payment.processor.crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.shiro.crypto.AesCipherService;
import org.supercsv.encoder.DefaultCsvEncoder;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.evenup.payment.processor.crypto.CryptoCsvEncoder.DIRECTION;

public class AesDecryptFileUtil {

    void decrypt(final String keyFilename, String inFilename, String outFilename)
            throws Exception {
        
        final CryptoCsvEncoder decryptingCsvEncoder = new CryptoCsvEncoder(
                new DefaultCsvEncoder(), new AesCipherService(),
                AesKeyManager.keyFromPath(keyFilename),
                DIRECTION.DECRYPTING);
        
        CsvPreference pref = new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useEncoder(decryptingCsvEncoder).build();
        try (CsvListWriter writer = 
                new CsvListWriter(
                        Files.newBufferedWriter(Paths.get(outFilename), Charset.defaultCharset()), 
                        pref)) {
            
            Reader reader = new BufferedReader(new FileReader(inFilename));
            CsvListReader listReader = new CsvListReader(reader, CsvPreference.STANDARD_PREFERENCE);
            List<String> row;
            while ((row = listReader.read()) != null) {
                writer.write(row);
            }
            listReader.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        new AesDecryptFileUtil().decrypt(args[0], args[1], args[2]);
    }

}
