package com.secretLab.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSATools
{
    /**
     *
     * @return String[] keypair    publickey in keypair[0] privatekey in keypair[1]
     * @throws IOException
     */
    public  static  String[] GenerateKeyPair() throws IOException {
        //生成密钥对
        RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGenerator();
        RSAKeyGenerationParameters rsaKeyGenerationParameters = new RSAKeyGenerationParameters(BigInteger.valueOf(3), new SecureRandom(), 1024, 25);
        rsaKeyPairGenerator.init(rsaKeyGenerationParameters);//初始化参数
        AsymmetricCipherKeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();

        AsymmetricKeyParameter publicKey = keyPair.getPublic();//公钥
        AsymmetricKeyParameter privateKey = keyPair.getPrivate();//私钥

        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(publicKey);
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfoFactory.createPrivateKeyInfo(privateKey);

        //变字符串
        ASN1Object asn1ObjectPublic = subjectPublicKeyInfo.toASN1Primitive();
        byte[] publicInfoByte = asn1ObjectPublic.getEncoded();
        ASN1Object asn1ObjectPrivate = privateKeyInfo.toASN1Primitive();
        byte[] privateInfoByte = asn1ObjectPrivate.getEncoded();

        //这里可以将密钥对保存到本地
//        final Base64.Encoder encoder64 = Base64.getEncoder();
//        System.out.println("PublicKey:\n" +  encoder64.encodeToString(publicInfoByte));
//        System.out.println("PrivateKey:\n" + encoder64.encodeToString(privateInfoByte));
        String publickeystr =  Base64.encodeBase64String(publicInfoByte);
        String privatekeystr = Base64.encodeBase64String(privateInfoByte);
        String[] strarray = new String[2];
        strarray[0] = publickeystr;
        strarray[1] = privatekeystr;
        return strarray;
    }

    private static AsymmetricKeyParameter getPublicKey(String pubkeystr) throws IOException {
        byte[] publicInfoBytes = Base64.decodeBase64(pubkeystr);
        ASN1Object pubkeyobj = ASN1Primitive.fromByteArray(publicInfoBytes);
        AsymmetricKeyParameter publickey = PublicKeyFactory.createKey(SubjectPublicKeyInfo.getInstance(pubkeyobj));
        return  publickey;
    }

    private static AsymmetricKeyParameter getPrivateKey(String prvkeystr) throws IOException {
        byte[] privateInfoBytes = Base64.decodeBase64(prvkeystr);
        AsymmetricKeyParameter privatekey = PrivateKeyFactory.createKey(privateInfoBytes);
        return privatekey;
    }

    public static String PublicKeyEncrypt(String publickey,String data)
    {
        String encodestring = null;
        try {
            AsymmetricKeyParameter pubkey = getPublicKey(publickey);
            AsymmetricBlockCipher cipher = new RSAEngine();
            cipher.init(true,pubkey);
            byte[] encryptDataBytes = cipher.processBlock(data.getBytes(),0,data.getBytes().length);
            encodestring = Base64.encodeBase64String(encryptDataBytes);
        }catch (IOException ioe){
            System.out.println("init publickey failed");
            ioe.printStackTrace();
        } catch (InvalidCipherTextException e) {
            System.out.println("encrypt failed");
            e.printStackTrace();
        }
        return encodestring;
    }

    public static String PrivateKeyDecrypt(String privatekey,String ciphertext)
    {
        String decodestring = null;
        try {
            AsymmetricKeyParameter prvkey = getPrivateKey(privatekey);
            AsymmetricBlockCipher cipher = new RSAEngine();
            byte[] ciphertextBytes = Base64.decodeBase64(ciphertext);
            cipher.init(false,prvkey);
            byte[] decryptDataBytes = cipher.processBlock(ciphertextBytes,0,ciphertextBytes.length);
            decodestring = new String(decryptDataBytes,"utf-8");
        } catch (IOException e) {
            System.out.println("init privatekey failed");
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            System.out.println("decrypt failed");
            e.printStackTrace();
        }

        return decodestring;
    }

    public static String PrivateKeyEncrypt(String privatekey , String data){
        String encodestring = null;
        try {
            AsymmetricKeyParameter prvkey = getPrivateKey(privatekey);
            AsymmetricBlockCipher cipher = new RSAEngine();
            cipher.init(true,prvkey);
            byte[] encryptDataBytes = cipher.processBlock(data.getBytes(),0,data.getBytes().length);
            encodestring = Base64.encodeBase64String(encryptDataBytes);
        }catch (IOException ioe){
            System.out.println("init privatekey failed");
            ioe.printStackTrace();
        } catch (InvalidCipherTextException e) {
            System.out.println("encrypt failed");
            e.printStackTrace();
        }
        return encodestring;
    }

    public static String PublicKeyDecrypt(String publickey , String ciphertext){
        String decodestring = null;
        try {
            AsymmetricKeyParameter pubkey = getPublicKey(publickey);
            AsymmetricBlockCipher cipher = new RSAEngine();
            byte[] ciphertextBytes = Base64.decodeBase64(ciphertext);
            cipher.init(false,pubkey);
            byte[] decryptDataBytes = cipher.processBlock(ciphertextBytes,0,ciphertextBytes.length);
            decodestring = new String(decryptDataBytes,"utf-8");
        } catch (IOException e) {
            System.out.println("init publickey failed");
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            System.out.println("decryptr failed");
            e.printStackTrace();
        }
        return decodestring;
    }
}

