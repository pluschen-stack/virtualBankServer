package com.secretLab.utils;

public class Constant {
  public static final String HASH_ALGORITHM = "SHA-256";//本项目中使用的hash算法
  public static final int SALT_LENGTH = 32;
  public static final String BLOCK_CIPHER = "AES";
  public static final String ENCRYPTION_ALGORITHM = BLOCK_CIPHER+"/CBC/PKCS7Padding";
  public static final int AES_IV_LENGTH = 16;
}
