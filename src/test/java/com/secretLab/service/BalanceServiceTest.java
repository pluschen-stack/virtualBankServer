package com.secretLab.service;

import org.junit.Test;

public class BalanceServiceTest {

  @Test
  public void testTransfer(){
    BalanceService.transfer("12345678900","12345678901",30);
  }

}
