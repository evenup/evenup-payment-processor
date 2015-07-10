package com.evenup.payment.processor.crypto;

import java.io.UnsupportedEncodingException;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.crypto.CryptoException;
import org.apache.shiro.util.ByteSource;
import org.supercsv.encoder.CsvEncoder;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.util.CsvContext;

/**
 * A Super CSV encoder that will encrypt every field using the Shiro
 * {@link CipherService} and key passed into the constructor.  Also contains
 * decryption.
 * <p>
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
public class EncryptingCsvEncoder implements CsvEncoder {
    private static final String DEFAULT_ENCODING = "ISO-8859-1";

    private final CsvEncoder encoder;
    private final CipherService cipherService;
    private byte[] key;

    public EncryptingCsvEncoder(CsvEncoder encoder, CipherService cipherService, byte[] key) {
        this.encoder = encoder;
        this.cipherService = cipherService;
        this.key = key;
    }

    @Override
    public String encode(String input, CsvContext context,
            CsvPreference preference) {
        String encoded = encoder.encode(input, context, preference);
        try {
            final byte[] bytesToEncrypt = CodecSupport.toBytes(encoded);
            final ByteSource encrypted = cipherService.encrypt(bytesToEncrypt, key);
            final String string = new String(Base64.encode(encrypted.getBytes()), DEFAULT_ENCODING);
            return string;
        } catch (CryptoException | UnsupportedEncodingException e) {
            throw new RuntimeException("Unable tp encode bytes.", e);
        }
    }

    public String decrypt(String input) {
        byte[] bytesToDecrypt;
        try {
            bytesToDecrypt = input.getBytes(DEFAULT_ENCODING);
            bytesToDecrypt = Base64.decode(bytesToDecrypt);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unable tp decode byte.", e);
        }
        ByteSource decrypted = cipherService.decrypt(bytesToDecrypt, key);
        final String string = CodecSupport.toString(decrypted.getBytes());
        return string;
    }

}
