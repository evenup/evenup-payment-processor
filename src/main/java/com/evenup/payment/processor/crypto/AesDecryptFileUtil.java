package com.evenup.payment.processor.crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.shiro.crypto.AesCipherService;
import org.supercsv.encoder.DefaultCsvEncoder;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.evenup.payment.processor.crypto.CryptoCsvEncoder.DIRECTION;

/**
 * Utility that decrypts a payment CSV file.
 * <p>
 * Copyright 2015 EvenUp, Inc.
 * <p>
 * THE  CODE IS  PROVIDED "AS  IS",  WITHOUT WARRANTY  OF ANY  KIND, EXPRESS  
 * OR IMPLIED,  INCLUDING BUT  NOT LIMITED  TO THE WARRANTIES  OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *
 * @author Kevin G. McManus
 *
 */
public class AesDecryptFileUtil {

    public void decrypt(final String keyFilename, Path inFilename, Path outFilename)
            throws Exception {
        
        final CryptoCsvEncoder decryptingCsvEncoder = new CryptoCsvEncoder(
                new DefaultCsvEncoder(), new AesCipherService(),
                AesKeyManager.keyFromPath(keyFilename),
                DIRECTION.DECRYPTING);
        
        CsvPreference pref = new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useEncoder(decryptingCsvEncoder).build();
        try (CsvListWriter writer = 
                new CsvListWriter(
                        Files.newBufferedWriter(outFilename, Charset.defaultCharset()), 
                        pref)) {
            
            Reader reader = new BufferedReader(new FileReader(inFilename.toFile()));
            CsvListReader listReader = new CsvListReader(reader, CsvPreference.STANDARD_PREFERENCE);
            List<String> row;
            while ((row = listReader.read()) != null) {
                writer.write(row);
            }
            listReader.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        new AesDecryptFileUtil().decrypt(args[0], Paths.get(args[1]), Paths.get(args[2]));
    }

}
