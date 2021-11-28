package com.secretLab.dao;

import com.secretLab.pojo.Account;
import com.secretLab.utils.MybatisUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class AccountDaoTest {
  @Test
  public void test(){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      AccountDao mapper = sqlSession.getMapper(AccountDao.class);
      List<Account> accountList = mapper.getAccounts();

      for (Account a:
      accountList) {
        System.out.println(a);
      }
    }
  }

  @Test
  public void testGetAccountByPhoneNumber(){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      AccountDao mapper = sqlSession.getMapper(AccountDao.class);
      Account account = mapper.getAccountByPhoneNumber("12345678901");
      System.out.println(account);
    }
  }

  @Test
  public void testInsertAccount(){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      AccountDao mapper = sqlSession.getMapper(AccountDao.class);
      mapper.insertAccount(new Account("hejihui","123456","not used","15515596423",36));
      //事务提交
      sqlSession.commit();
    }
  }

  @Test
  public void testUpdateAccount(){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      AccountDao mapper = sqlSession.getMapper(AccountDao.class);
      mapper.updateAccountName("12345678900","chenHao");
      //事务提交
      sqlSession.commit();
    }
  }

  @Test
  public void testDeleteAccount(){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      AccountDao mapper = sqlSession.getMapper(AccountDao.class);
      mapper.deleteAccount("15515596423");
      //事务提交
      sqlSession.commit();
    }
  }

  @Test
  public void testUpdateAccountBalance(){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      AccountDao mapper = sqlSession.getMapper(AccountDao.class);
      String phoneNumber = "12345678900";
      String balance = "60";
      mapper.updateAccountBalance(phoneNumber,balance);
      //事务提交
      sqlSession.commit();
    }
  }
}
