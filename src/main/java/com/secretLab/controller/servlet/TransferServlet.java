package com.secretLab.controller.servlet;

import com.alibaba.fastjson.JSON;
import com.secretLab.service.BalanceService;
import com.secretLab.utils.AESUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransferServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String secretKey = (String)req.getSession().getAttribute("secretKey");
    String iv = (String) req.getSession().getAttribute("iv");
    String srcPhoneNumber = AESUtil.decrypt(req.getParameter("srcPhoneNumber"),secretKey,iv);
    String destPhoneNumber = AESUtil.decrypt(req.getParameter("destPhoneNumber"),secretKey,iv);
    String transferAmount = AESUtil.decrypt(req.getParameter("transferAmount"),secretKey,iv);
    boolean transferFlag = BalanceService.transfer(srcPhoneNumber, destPhoneNumber, Integer.parseInt(transferAmount));
    Map<String,String> resMap = new HashMap<>();
    resMap.put("transferFlag",String.valueOf(transferFlag));
    resp.getWriter().print(AESUtil.encrypt(JSON.toJSONString(resMap),secretKey,iv));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }
}
