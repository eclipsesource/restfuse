package com.eclipsesource.rest.testhelper;

import org.junit.Before;
import org.junit.Test;

import com.eclipsesource.restfuse.Request;


public class Request_Test {
  
  private Request request;

  @Before
  public void setUp() {
//    request = new Request();
  }
  
  @Test( expected = IllegalStateException.class )
  public void testCantSendRequest() {
    request.getResponse();
  }
}
