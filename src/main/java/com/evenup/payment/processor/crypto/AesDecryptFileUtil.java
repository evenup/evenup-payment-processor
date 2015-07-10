package com.evenup.payment.processor.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.AesCipherService;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class AesDecryptFileUtil {

    @SuppressWarnings("unchecked")
    void decrypt(final String keyFilename, String inFilename, String outFilename)
            throws Exception {
        
        final EncryptingCsvEncoder encryptingCsvEncoder = new EncryptingCsvEncoder(
                null, new AesCipherService(),
                AesKeyManager.keyFromPath(keyFilename));
        
        Function<String, String> decrypt = new Function<String, String>() {

            @Override
            public String apply(String input) {
                return encryptingCsvEncoder.decrypt(input);
            }
            
        };
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFilename), Charset.defaultCharset())) {

            Reader reader = new BufferedReader(new FileReader(inFilename));
            CsvListReader listReader = new CsvListReader(reader, CsvPreference.STANDARD_PREFERENCE);
            List<String> row;
            while ((row = listReader.read()) != null) {
                writer.write(StringUtils.join(Iterables.transform(row, decrypt)));
                writer.newLine();
            }
            listReader.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        new AesDecryptFileUtil().decrypt(args[0], args[1], args[2]);
    }

}
