package com.secretLab.utils;

import static com.secretLab.utils.Constant.KEY_MAC;
import static java.nio.charset.StandardCharsets.US_ASCII;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 这个类实现了一些对摘要算法的包装
 */
public class MacUtil {

  /**
   * 指定算法对消息取摘要
   * @param message 任意长度的字符串
   * @param algorithm 可以使用SHA256,SHA1等在java标准的hash函数
   * @return 摘要的字节数组
   */
  public static String digest(String message,String algorithm) throws NoSuchAlgorithmException {
    MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
    return Hex.encodeHexString(messageDigest.digest(message.getBytes(US_ASCII)));
  }

  /**
   * 生成盐值
   * @param length 指定盐值的长度，以字节为单位
   * @return 盐值的字节数组
   */
  public static String generateSalt(int length){
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[length];
    random.nextBytes(salt);
    return Hex.encodeHexString(salt);
  }

  public static String initMacKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
    SecretKey secretKey = keyGenerator.generateKey();
    return encryptBase64(secretKey.getEncoded());
  }

  public static String encryptHMAC(String data, String key) throws Exception {
    SecretKey secretKey = new SecretKeySpec(decryptBase64(key), KEY_MAC);
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    mac.init(secretKey);
    return encryptBase64(mac.doFinal(decryptBase64(data)));
  }

  public static String encryptBase64(byte[] message) throws Exception {
    return Base64.encodeBase64String(message);
  }

  public static byte[] decryptBase64(String message) throws Exception {
    return Base64.decodeBase64(message);
  }

}
