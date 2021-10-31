package com.secretLab.controller.servlet;

import static com.secretLab.utils.NetWorkConstant.SYMMETRIC_KEY_INFORMATION;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
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
    String symmetricKeyInformationJson = req.getParameter(SYMMETRIC_KEY_INFORMATION);
    if(symmetricKeyInformationJson!=null){
      JSONObject jsonObject = JSONObject.parseObject(symmetricKeyInformationJson);
      String secretKey = jsonObject.getString("secretKey");
      String iv = jsonObject.getString("iv");
      session.setAttribute("secretKey",secretKey);
      session.setAttribute("iv",iv);
      resp.getWriter().write("OK");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }
}
