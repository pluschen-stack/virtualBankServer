package com.secretLab.controller.servlet;

import com.alibaba.fastjson.JSON;
import com.secretLab.pojo.Record;
import com.secretLab.service.RecordsService;
import com.secretLab.utils.AESUtil;
import com.secretLab.utils.MacUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryRecordsServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String secretKey = (String)req.getSession().getAttribute("secretKey");
    String iv = (String) req.getSession().getAttribute("iv");
    String secretMacKey = (String) req.getSession().getAttribute("secretMacKey");
    String mac = req.getParameter("mac");
    String phoneNumberEncrypted = req.getParameter("phoneNumber");
    try {
      if(MacUtil.encryptHMAC(phoneNumberEncrypted,secretMacKey).equals(mac)){
        String phoneNumber = AESUtil.decrypt(phoneNumberEncrypted,secretKey,iv);
        List<Record> records = RecordsService.queryRecords(phoneNumber);
        Map<String,String> resMap = new HashMap<>();
        resMap.put("records", JSON.toJSONString(records));
        resp.getWriter().print(
            AESUtil.encrypt(JSON.toJSONString(resMap), secretKey, iv));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }
}
