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
 * A Super CSV encoder that will encrypt/decrypt every field using the Shiro
 * {@link CipherService} and key passed into the constructor.  Also contains
 * decryption.
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
public class CryptoCsvEncoder implements CsvEncoder {
    private static final String DEFAULT_ENCODING = "ISO-8859-1";

    private final CsvEncoder encoder;
    private final CipherService cipherService;
    private final byte[] key;
    private final DIRECTION direction;

    // N.B. Don't ever add to this.
    public static enum DIRECTION {
        ENCRYPTING,
        DECRYPTING
    }
    
    public CryptoCsvEncoder(CsvEncoder encoder, CipherService cipherService, byte[] key, DIRECTION direction) {
        this.encoder = encoder;
        this.cipherService = cipherService;
        this.key = key;
        this.direction = direction;
    }

    @Override
    public String encode(String input, CsvContext context,
            CsvPreference preference) {
        String encoded = encoder.encode(input, context, preference);
        return direction == DIRECTION.ENCRYPTING ? encrypt(encoded) : decrypt(encoded);
    }

    private String encrypt(String encoded) {
        try {
            final byte[] bytesToEncrypt = CodecSupport.toBytes(encoded);
            final ByteSource encrypted = cipherService.encrypt(bytesToEncrypt, key);
            final String string = new String(Base64.encode(encrypted.getBytes()), DEFAULT_ENCODING);
            return string;
        } catch (CryptoException | UnsupportedEncodingException e) {
            throw new RuntimeException("Unable tp encode bytes.", e);
        }
    }

    private String decrypt(String input) {
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
