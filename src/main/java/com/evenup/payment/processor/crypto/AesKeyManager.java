package com.evenup.payment.processor.crypto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;

import com.google.inject.Singleton;

/**
 * 
 * Copyright 2014 EvenUp, Inc.
 *
 * @author Kevin G. McManus
 *
 */
@Singleton
public class AesKeyManager {
    
    private AesCipherService cipherService = new AesCipherService();

    private void generate(String filename, int keySize) throws IOException,
            NoSuchAlgorithmException {
        byte[] encoded = Base64.encode(cipherService.generateNewKey(keySize)
                .getEncoded());
        Files.write(Paths.get(filename), encoded);
    }


    public static byte[] keyFromPath(String path)
            throws IOException {
        return Base64.decode(Files.readAllBytes(Paths.get(path)));
    }
    
    public static void main(String[] args) throws Exception {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("AesKeyManager")
        .defaultHelp(true)
        .description("Generates an AES Key compatible with the payment processor.");
        parser.addArgument("file").help("the file the key will be written to").required(true);
        parser.addArgument("-s","--size").help("the size in bits for the generated key (128, 192 and 256).  if greater than 128, java requires extra jars added to the JVM").setDefault(128);
        
        Namespace ns = null;
        ns = parser.parseArgsOrFail(args);
        
        // in order to go higher than 128, we need to get jars from the following and copy to:
        // ${java.home}/jre/lib/security/
        // http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html  OR
        // http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
        new AesKeyManager().generate(ns.getString("file"), ns.getInt("size"));
    }

}
