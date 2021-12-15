package com.secretLab.pojo;

public class Account {

  private String userName;//用户名
  private String password;//hash与加盐加密后的密码
  private String salt;//盐值
  private String phoneNumber;//用来作主键，并且登录就靠它了
  private double balance;//余额
  private int payPassword;//支付口令
  private String CASeqNum;

  public Account(String userName, String password, String salt, String phoneNumber, double balance,
      int payPassword, String CASeqNum) {
    this.userName = userName;
    this.password = password;
    this.salt = salt;
    this.phoneNumber = phoneNumber;
    this.balance = balance;
    this.payPassword = payPassword;
    this.CASeqNum = CASeqNum;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "Account{" +
        "userName='" + userName + '\'' +
        ", password='" + password + '\'' +
        ", salt='" + salt + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", balance=" + balance +
        ", payPassword=" + payPassword +
        ", CASeqNum='" + CASeqNum + '\'' +
        '}';
  }
}
