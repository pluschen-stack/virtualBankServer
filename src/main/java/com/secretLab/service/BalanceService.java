package com.secretLab.service;

import com.secretLab.dao.AccountDao;
import com.secretLab.dao.RecordDao;
import com.secretLab.pojo.Account;
import com.secretLab.utils.MybatisUtils;
import java.time.LocalDateTime;
import org.apache.ibatis.session.SqlSession;

/**
 * 这个类提供转账，充值等操作
 */
public class BalanceService {

  /**
   * @param srcPhoneNumber 转账源账户
   * @param destPhoneNumber 转账目标账户
   * @param transferAmount 转账金额
   * @return boolean 如果有足够的钱转账就转账并返回true，否则返回false
   */
  public static boolean transfer(String srcPhoneNumber,String destPhoneNumber,int transferAmount){
    if(transferAmount < 0 || destPhoneNumber.equals(srcPhoneNumber)) return false;
    else{
      try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
        AccountDao accountDaoMapper = sqlSession.getMapper(AccountDao.class);
        RecordDao recordDaoMapper = sqlSession.getMapper(RecordDao.class);
        Account srcAccount = accountDaoMapper.getAccountByPhoneNumber(srcPhoneNumber);
        Account destAccount = accountDaoMapper.getAccountByPhoneNumber(destPhoneNumber);
        if(transferAmount <= srcAccount.getBalance()){
          accountDaoMapper.updateAccountBalance(srcPhoneNumber,String.valueOf(srcAccount.getBalance()-transferAmount));
          accountDaoMapper.updateAccountBalance(destPhoneNumber,String.valueOf(destAccount.getBalance()+transferAmount));
          recordDaoMapper.addRecord(srcPhoneNumber,destPhoneNumber,String.valueOf(transferAmount), LocalDateTime.now().toString());
        }else{
          return false;
        }
        //事务提交
        sqlSession.commit();
        return true;
      }catch (Exception e){
        System.out.println("目标账户不存在");
        return false;
      }
    }
  }

  public static String queryBalance(String phoneNumber){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      AccountDao accountDaoMapper = sqlSession.getMapper(AccountDao.class);
      Account accountByPhoneNumber = accountDaoMapper.getAccountByPhoneNumber(phoneNumber);
      return String.valueOf(accountByPhoneNumber.getBalance());
    }
  }

//  /**
//   * 执行充值功能
//   * @param destPhoneNumber 需要充值的账户
//   * @param amount 充值的金额
//   * @return 如果充值成功就返回true，否则返回false
//   */
//  public static boolean recharge(String destPhoneNumber,int amount){
//    if(amount < 0) throw new RuntimeException("充值金额出错");
//    else{
//      try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
//        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
//        Account destAccount = mapper.getAccountByPhoneNumber(destPhoneNumber);
//        mapper.updateAccountBalance(destPhoneNumber,String.valueOf(destAccount.getBalance()+amount));
//        sqlSession.commit();
//        return true;
//      }
//    }
//  }
}
