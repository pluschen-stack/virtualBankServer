package com.secretLab.dao;

import com.secretLab.pojo.Account;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

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
   * 删除用户
   */
  void deleteAccount(String phoneNumber);

  /**
   * 修改用户名
   */
  void updateAccountName(@Param("phoneNumber") String phoneNumber,@Param("userName") String userName);

  /**
   * 修改余额
   */
  void updateAccountBalance(@Param("phoneNumber") String phoneNumber,@Param("balance") String balance);
}
