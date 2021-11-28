package com.secretLab.dao;

import com.secretLab.pojo.Account;
import com.secretLab.pojo.Record;
import com.secretLab.utils.MybatisUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class RecordDaoTest {

  @Test
  public void testAddRecord(){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      RecordDao mapper = sqlSession.getMapper(RecordDao.class);
      LocalDateTime time = LocalDateTime.now();
      System.out.println(time);
      mapper.addRecord("12345678901","12345678900",String.valueOf(30),time.toString());
      //事务提交
      sqlSession.commit();
    }
  }

  @Test
  public void testGetRecords(){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      RecordDao mapper = sqlSession.getMapper(RecordDao.class);
      ArrayList<Record> records = (ArrayList<Record>) mapper.getRecords("12345678900");
      for (Record record:
      records) {
        System.out.println(record);
      }
    }
  }
}
