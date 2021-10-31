package com.secretLab.utils;

import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 实现了对Mybatis的封装
 */
public class MybatisUtils {
  private static SqlSessionFactory sqlSessionFactory;

  static{
    try{
      String resource = "mybatis-config.xml";
      InputStream inputStream = Resources.getResourceAsStream(resource);
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 提供sqlSession
   * @return
   */
  public static SqlSession getSqlSession(){
    return sqlSessionFactory.openSession();
  }
  
}
