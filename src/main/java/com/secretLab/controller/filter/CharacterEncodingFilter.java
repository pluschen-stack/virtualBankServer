package com.secretLab.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 使得请求和响应都设置为utf-8
 */
public class CharacterEncodingFilter implements Filter {


  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("create CharacterEncodingFilter");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    chain.doFilter(request,response);
  }

  @Override
  public void destroy() {
    System.out.println("destroy CharacterEncodingFilter");
  }
}
