package codingTest;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;
import org.bouncycastle.openssl.PEMParser;

/**
 * A simple utility class that converts PEM PKCS8 (OpenSSL) to a RSA PGPPublicKey/PGPSecretKey pair.
 * <p>
 * usage: UnixSE276317 [-a] identity passPhrase inputPEM
 * <p>
 * Where identity is the name to be associated with the public key. The keys are placed
 * in the files {pub,secret}.asc if -a (armor) is specified and .bpg otherwise.
 */
//modified from package org.bouncycastle.openpgp.examples class RSAPrivateKeyGenerator
public class PGPTest
{
    private static void exportKeyPair(
        OutputStream    secretOut,
        OutputStream    publicOut,
        KeyPair         pair,
        String          identity,
        char[]          passPhrase,
        boolean         armor)
        throws IOException, InvalidKeyException, NoSuchProviderException, SignatureException, PGPException
    {
        if (armor)
        {
            secretOut = new ArmoredOutputStream(secretOut);
        }

        PGPDigestCalculator sha1Calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1);
        PGPKeyPair          keyPair = new JcaPGPKeyPair(PGPPublicKey.RSA_GENERAL, pair, new Date());
        PGPSecretKey        secretKey = new PGPSecretKey(PGPSignature.DEFAULT_CERTIFICATION, keyPair, identity, sha1Calc, null, null,
                new JcaPGPContentSignerBuilder(keyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1),
                new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.CAST5, sha1Calc).setProvider("BC").build(passPhrase));


        	
            secretKey.encode(secretOut);

            secretOut.close();

            if (armor)
            {
                publicOut = new ArmoredOutputStream(publicOut);
            }

            PGPPublicKey    key = secretKey.getPublicKey();

            key.encode(publicOut);

            publicOut.close();
        }

        public static void main(
            String[] args)
            throws Exception
        {
            Security.addProvider(new BouncyCastleProvider());

            //KeyPairGenerator    kpg = KeyPairGenerator.getInstance("RSA", "BC");

            //kpg.initialize(1024);

            //KeyPair                    kp = kpg.generateKeyPair();

            int flag = args.length > 0 && args[0].equals("-a")? 1: 0;

            if (args.length != flag+3)
            {
                System.out.println("PGPTest [-a] identity passPhrase inputPEM");
                System.exit(0);
            }

            // adapted from org.bouncycastle.openssl.PEMParser$PrivateKeyParser+RSAKeyPairParser
            FileReader rdr = new FileReader (args[flag+2]);
            PrivateKeyInfo pk8 = (PrivateKeyInfo) new PEMParser(rdr).readObject();
            rdr.close();
            ASN1Sequence seq = (ASN1Sequence) pk8.parsePrivateKey();
            org.bouncycastle.asn1.pkcs.RSAPrivateKey keyStruct = org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(seq);
            KeyFactory fact = KeyFactory.getInstance ("RSA");
            KeySpec privSpec = new PKCS8EncodedKeySpec (pk8.getEncoded());
            KeySpec pubSpec = new RSAPublicKeySpec(keyStruct.getModulus(), keyStruct.getPublicExponent());
            KeyPair kp = new KeyPair (fact.generatePublic(pubSpec), fact.generatePrivate(privSpec));

            String[] suffix = {"bpg","asc"};
            FileOutputStream    out1 = new FileOutputStream("secret."+suffix[flag]);
            FileOutputStream    out2 = new FileOutputStream("pub."+suffix[flag]);
            exportKeyPair(out1, out2, kp, args[flag+0], args[flag+1].toCharArray(), flag>0);
        }
    }
