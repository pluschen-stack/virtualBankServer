package com.secretLab.controller.servlet;

import static com.secretLab.utils.NetWorkConstant.CA_LICENSE;
import static com.secretLab.utils.NetWorkConstant.SESSION_ID;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CAServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    String sessionId = session.getId();
    if(req.getParameter(CA_LICENSE).equals("NEED")){
      Map<String,String> resMap = new HashMap<>();
      resMap.put(CA_LICENSE,"ok");
      resp.getWriter().print(JSON.toJSONString(resMap));
      resp.addCookie(new Cookie(SESSION_ID,sessionId));
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }
}
