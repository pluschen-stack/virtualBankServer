package com.secretLab.pojo;

import java.util.Arrays;
import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Hex;

public class SymmetricKeyInformation {
  private String sessionId;
  private SecretKey secretKey;
  private String iv;

  public SecretKey getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(SecretKey secretKey) {
    this.secretKey = secretKey;
  }

  public byte[] getIv() {
    return iv.getBytes();
  }

  public void setIv(byte[] iv) {
    this.iv = Hex.encodeHexString(iv);
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public String toString() {
    return "SymmetricKeyInformation{" +
        "sessionId='" + sessionId + '\'' +
        ", secretKey=" + secretKey +
        ", iv=" + iv +
        '}';
  }
}
