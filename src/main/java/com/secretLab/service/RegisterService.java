package com.secretLab.service;

import static com.secretLab.utils.Constant.HASH_ALGORITHM;
import static com.secretLab.utils.Constant.SALT_LENGTH;

import com.secretLab.dao.AccountDao;
import com.secretLab.pojo.Account;
import com.secretLab.utils.Mac;
import com.secretLab.utils.MybatisUtils;
import java.security.NoSuchAlgorithmException;
import org.apache.ibatis.session.SqlSession;

/**
 * 提供安全的注册服务
 */
public class RegisterService {

  /**
   * 进行注册服务，如果数据库中没有对应的手机号就可以注册，否则无法注册
   * @param phoneNumber 明文手机号
   * @param password hash后的密码
   * @return 如果成功注册就返回true,否则返回false
   */
  public static boolean register(String phoneNumber,String password)  {
    if(phoneNumber == null || password == null){
      return false;
    }
    boolean registerFlag = false;
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      String salt = Mac.generateSalt(SALT_LENGTH);
      password = Mac.digest(password+salt,HASH_ALGORITHM);
      AccountDao mapper = sqlSession.getMapper(AccountDao.class);
      if(mapper.getAccountByPhoneNumber(phoneNumber)==null){
        mapper.insertAccount(new Account("自定义用户名",password,salt,phoneNumber,0));
        sqlSession.commit();
        registerFlag = true;
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return registerFlag;
  }



  public static void main(String[] args) throws NoSuchAlgorithmException {
    if(register("12345678901",Mac.digest("123456",HASH_ALGORITHM))){
      System.out.println("注册成功");
    }else{
      System.out.println("注册失败");
    }
  }
}
