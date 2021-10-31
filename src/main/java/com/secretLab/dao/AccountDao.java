package com.secretLab.dao;

import com.secretLab.pojo.Account;
import java.util.List;

public interface AccountDao {

  /**
   * 查询所有Account
   * @return 返回一个list，里面是Account对象
   */
  List<Account> getAccounts();

  /**
   * 根据手机号查询账户
   * @return 如果查到就返回指定账户信息
   * @param phoneNumber
   */
  Account getAccountByPhoneNumber(String phoneNumber);

  /**
   * 插入一个账户
   */
  void insertAccount(Account account);

  /**
   * 更新一个用户
   */
  void updateAccount(Account account);

  /**
   * 删除用户
   */
  void deleteAccount(String phoneNumber);
}
