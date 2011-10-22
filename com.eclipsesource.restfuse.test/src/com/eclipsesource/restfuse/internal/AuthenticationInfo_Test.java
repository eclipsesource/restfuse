package com.eclipsesource.restfuse.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.eclipsesource.restfuse.AuthenticationType;


public class AuthenticationInfo_Test {
  
  private AuthenticationInfo info;

  @Before
  public void setUp() {
    info = new AuthenticationInfo( AuthenticationType.BASIC, "user", "password" );
  }
  
  @Test
  public void testGetType() {
    assertEquals( AuthenticationType.BASIC, info.getType() );
  }
  
  @Test
  public void testGetUser() {
    assertEquals( "user", info.getUser() );
  }
  
  @Test
  public void testGetPassword() {
    assertEquals( "password", info.getPassword() );
  }
}
