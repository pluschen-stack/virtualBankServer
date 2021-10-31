package com.secretLab.service;

import static com.secretLab.utils.Constant.HASH_ALGORITHM;

import com.secretLab.dao.AccountDao;
import com.secretLab.pojo.Account;
import com.secretLab.utils.Mac;
import com.secretLab.utils.MybatisUtils;
import java.security.NoSuchAlgorithmException;
import org.apache.ibatis.session.SqlSession;

public class LoginService {

  /**
   * 进行登录验证功能
   * @param phoneNumber 明文手机号
   * @param password hash运算过的密码
   * @return 如果登录成功返回True，否则返回false
   */
  public static boolean login(String phoneNumber,String password) {
    if(phoneNumber == null || password == null){
      return false;
    }
    boolean LoginFlag = false;
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      AccountDao mapper = sqlSession.getMapper(AccountDao.class);
      Account account = mapper.getAccountByPhoneNumber(phoneNumber);
      if(account != null){
        String salt = account.getSalt();
        if(account.getPassword().equals(Mac.digest(password+salt,HASH_ALGORITHM))){
          LoginFlag = true;
        }
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return LoginFlag;
  }

  public static void main(String[] args) throws NoSuchAlgorithmException {
    if(login("12345678901",Mac.digest("123456",HASH_ALGORITHM))){
      System.out.println("登录成功");
    }else{
      System.out.println("登录失败");
    }
  }
}
