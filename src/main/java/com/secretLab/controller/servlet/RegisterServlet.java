package com.secretLab.controller.servlet;

import com.alibaba.fastjson.JSON;
import com.secretLab.service.RegisterService;
import com.secretLab.utils.AESUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String secretKey = (String)req.getSession().getAttribute("secretKey");
    String iv = (String) req.getSession().getAttribute("iv");
    String phoneNumber = AESUtil.decrypt(req.getParameter("phoneNumber"),secretKey,iv);
    String password = AESUtil.decrypt(req.getParameter("password"),secretKey,iv);
    String accountName = AESUtil.decrypt(req.getParameter("accountName"),secretKey,iv);
    String payPassword = AESUtil.decrypt(req.getParameter("payPassword"),secretKey,iv);
    Map<String,Boolean> resMap = new HashMap<>();
    if(RegisterService.register(phoneNumber,password,accountName,payPassword)){
      resMap.put("registerFlag",true);
    }else{
      resMap.put("registerFlag",false);
    }
    resp.getWriter().print(AESUtil.encrypt(JSON.toJSONString(resMap),secretKey,iv));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req,resp);
  }
}
