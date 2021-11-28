package com.secretLab.service;

import com.secretLab.dao.RecordDao;
import com.secretLab.pojo.Record;
import com.secretLab.utils.MybatisUtils;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public class RecordsService {

  public static List<Record> queryRecords(String phoneNumber){
    try(SqlSession sqlSession = MybatisUtils.getSqlSession()){
      RecordDao recordDaoMapper = sqlSession.getMapper(RecordDao.class);
      return recordDaoMapper.getRecords(phoneNumber);
    }
  }
}
