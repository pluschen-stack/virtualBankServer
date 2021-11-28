package com.secretLab.utils;

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Hex;

/**
 * 这个类实现了一些对摘要算法的包装
 */
public class Mac {

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



}
