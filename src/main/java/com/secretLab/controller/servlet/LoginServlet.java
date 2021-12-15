package com.secretLab.controller.servlet;

import com.alibaba.fastjson.JSON;
import com.secretLab.dao.AccountDao;
import com.secretLab.pojo.Account;
import com.secretLab.service.LoginService;
import com.secretLab.utils.AESUtil;
import com.secretLab.utils.MacUtil;
import com.secretLab.utils.MybatisUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;

public class LoginServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String secretKey = (String)req.getSession().getAttribute("secretKey");
    String iv = (String) req.getSession().getAttribute("iv");
    String secretMacKey = (String) req.getSession().getAttribute("secretMacKey");
    String phoneNumberEncrypted = (String) req.getParameter("phoneNumber");
    String passwordEncrypted = (String) req.getParameter("password");
    String mac = (String) req.getParameter("mac");
    Map<String,String> resMap = new HashMap<>();
    try {
      String realMac = MacUtil.encryptHMAC(phoneNumberEncrypted + passwordEncrypted, secretMacKey);
      if(!mac.equals(realMac)){
        resMap.put("loginFlag","false");
        resp.getWriter().print(AESUtil.encrypt(JSON.toJSONString(resMap),secretKey,iv));
        return;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    String phoneNumber = AESUtil.decrypt(phoneNumberEncrypted,secretKey,iv);
    String password = AESUtil.decrypt(passwordEncrypted ,secretKey,iv);
    if(LoginService.login(phoneNumber,password)){
      resMap.put("loginFlag","true");
      try(SqlSession sqlSession = MybatisUtils.getSqlSession()) {
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        Account account = mapper.getAccountByPhoneNumber(phoneNumber);
        resMap.put("accountName",account.getUserName());
        resMap.put("balance",String.valueOf(account.getBalance()));
      }
    }else{
      resMap.put("loginFlag","false");
    }
    resp.getWriter().print(AESUtil.encrypt(JSON.toJSONString(resMap),secretKey,iv));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req,resp);
  }
}
