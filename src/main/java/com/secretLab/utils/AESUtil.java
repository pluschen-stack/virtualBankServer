package com.secretLab.utils;

import static com.secretLab.utils.Constant.AES_IV_LENGTH;
import static com.secretLab.utils.Constant.BLOCK_CIPHER;
import static com.secretLab.utils.Constant.ENCRYPTION_ALGORITHM;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
/**
 * 这个类实现对AES算法的一个包装
 */
public class AESUtil {

  public static String createSecretKey(){
    SecretKey secretKey = null;
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance(BLOCK_CIPHER);
      keyGenerator.init(128);
      secretKey = keyGenerator.generateKey();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return Hex.encodeHexString(secretKey.getEncoded());
  }

  public static String createIV(int length){
    SecureRandom random = new SecureRandom();
    byte[] iv = new byte[length];
    random.nextBytes(iv);
    return Hex.encodeHexString(iv);
  }

  public static String encrypt(String message,String secretKey,String iv){

    Security.addProvider(new BouncyCastleProvider());
    byte[] messageEncrypted = null;
    try {
      SecretKeySpec key = new SecretKeySpec(Hex.decodeHex(secretKey), BLOCK_CIPHER);// 转换为AES专用密钥
      byte[] ivKey = Hex.decodeHex(iv);
      Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
      IvParameterSpec ivParameterSpec = new IvParameterSpec(ivKey);
      cipher.init(Cipher.ENCRYPT_MODE,key, ivParameterSpec);
      messageEncrypted = cipher.doFinal(message.getBytes());
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | DecoderException e) {
      e.printStackTrace();
    }
    if (messageEncrypted != null) {
      return Hex.encodeHexString(messageEncrypted);
    }else{
      return null;
    }
  }

  public static String decrypt(String messageEncrypted,String secretKey,String iv) {
    Security.addProvider(new BouncyCastleProvider());
    byte[] message = null;
    try {
      SecretKeySpec key = new SecretKeySpec(Hex.decodeHex(secretKey), BLOCK_CIPHER);// 转换为AES专用密钥
      byte[] ivKey = Hex.decodeHex(iv);
      byte[] messageEncryptedByte = Hex.decodeHex(messageEncrypted);
      Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
      IvParameterSpec ivParameterSpec = new IvParameterSpec(ivKey);
      cipher.init(Cipher.DECRYPT_MODE,key,ivParameterSpec);
      message = cipher.doFinal(messageEncryptedByte);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | DecoderException e) {
      e.printStackTrace();
    }
    if (message != null) {
      return new String(message);
    }else{
      return null;
    }
  }

  public static void main(String[] args) {
    String secretKey = createSecretKey();
    String iv = createIV(AES_IV_LENGTH);
    String s = encrypt("格物楼里的教室好吵，是谁在装修啊啊", secretKey, iv);
    System.out.println(s);
    System.out.println(decrypt(s,secretKey,iv));
    s = encrypt("格物楼里的教室好吵，是谁在装修啊啊", secretKey, iv);
    System.out.println(s);
    System.out.println(decrypt(s,secretKey,iv));
  }
}
