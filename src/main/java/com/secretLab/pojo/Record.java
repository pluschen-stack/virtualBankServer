package com.secretLab.pojo;

import java.time.LocalDateTime;
import java.util.Date;//我记得这个好像是mutable类。但我忘了immutable的那个是哪个了。

public class Record {
  private String srcPhoneNumber;
  private String destPhoneNumber;
  private int amount;
  private LocalDateTime time;

  public Record(String srcPhoneNumber, String destPhoneNumber, int amount, LocalDateTime time) {
    this.srcPhoneNumber = srcPhoneNumber;
    this.destPhoneNumber = destPhoneNumber;
    this.amount = amount;
    this.time = time;
  }

  public String getSrcPhoneNumber() {
    return srcPhoneNumber;
  }

  public void setSrcPhoneNumber(String srcPhoneNumber) {
    this.srcPhoneNumber = srcPhoneNumber;
  }

  public String getDestPhoneNumber() {
    return destPhoneNumber;
  }

  public void setDestPhoneNumber(String destPhoneNumber) {
    this.destPhoneNumber = destPhoneNumber;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "Record{" +
        "srcPhoneNumber='" + srcPhoneNumber + '\'' +
        ", destPhoneNumber='" + destPhoneNumber + '\'' +
        ", amount=" + amount +
        ", time=" + time.toString() +
        '}';
  }
}
