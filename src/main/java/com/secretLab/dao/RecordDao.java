package com.secretLab.dao;

import com.secretLab.pojo.Record;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecordDao {

  /**
   * 获取指定手机号账户的所有交易记录
   * @return 该手机账户的所有交易记录
   */
  List<Record> getRecords(String phoneNumber);

  /**
   * 添加一条记录
   */
  void addRecord(@Param("srcPhoneNumber") String srcPhoneNumber,@Param("destPhoneNumber") String destPhoneNumber,
      @Param("amount")String amount,@Param("time")String time);
}
