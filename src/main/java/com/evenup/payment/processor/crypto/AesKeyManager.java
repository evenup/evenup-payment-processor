package com.evenup.payment.processor.crypto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;

import com.google.inject.Singleton;

@Singleton
public class AesKeyManager {
    
    private AesCipherService cipherService = new AesCipherService();

    private void generate(String filename) throws IOException,
            NoSuchAlgorithmException {
        byte[] encoded = Base64.encode(cipherService.generateNewKey()
                .getEncoded());
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(encoded);
        }
    }


    public static byte[] keyFromPath(String path)
            throws IOException {
        return Base64.decode(Files.readAllBytes(Paths.get(path)));
    }
    public static void main(String[] args) throws Exception {
        new AesKeyManager().generate(args[0]);
    }

}
