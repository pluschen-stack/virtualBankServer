package com.secretLab.controller.servlet;

import com.alibaba.fastjson.JSON;
import com.secretLab.service.RegisterService;
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
    String phoneNumber = req.getParameter("phoneNumber");
    String password = req.getParameter("password");
    Map<String,Boolean> resMap = new HashMap<>();
    if(RegisterService.register(phoneNumber,password)){
      resMap.put("registerFlag",true);
    }else{
      resMap.put("registerFlag",false);
    }
    resp.getWriter().print(JSON.toJSONString(resMap));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req,resp);
  }
}
