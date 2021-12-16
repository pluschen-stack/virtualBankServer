package com.secretLab.controller.servlet;

import static com.secretLab.utils.Constant.BANK_RSA;
import static com.secretLab.utils.NetWorkConstant.SYMMETRIC_KEY_INFORMATION;

import com.alibaba.fastjson.JSONObject;
import com.secretLab.utils.MacUtil;
import com.secretLab.utils.RSATools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExchangePrivateKeyServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    StringBuilder stringBuilder = new StringBuilder();
    File file = new File(BANK_RSA);
    try {
      FileInputStream fis = new FileInputStream(file);
      BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
      String temp;
      while((temp = bf.readLine())!= null){
        stringBuilder.append(temp).append("\n");
      }
      String bankRSA = stringBuilder.toString();
      String privateKey = bankRSA.split("\\n")[1].split(":")[1];
      String secretKey = RSATools.PrivateKeyDecrypt(privateKey,(String) req.getParameter(SYMMETRIC_KEY_INFORMATION+"1"));
      String iv = RSATools.PrivateKeyDecrypt(privateKey,(String) req.getParameter(SYMMETRIC_KEY_INFORMATION+"2"));
      String secretMacKey = RSATools.PrivateKeyDecrypt(privateKey,(String) req.getParameter(SYMMETRIC_KEY_INFORMATION+"3"));
      session.setAttribute("secretKey",secretKey);
      session.setAttribute("iv",iv);
      session.setAttribute("secretMacKey",secretMacKey);
      resp.getWriter().write("OK");
      bf.close();
      fis.close();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }
}
