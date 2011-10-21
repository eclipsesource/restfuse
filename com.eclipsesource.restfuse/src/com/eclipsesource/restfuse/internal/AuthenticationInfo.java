package com.eclipsesource.restfuse.internal;

import com.eclipsesource.restfuse.AuthenticationType;


public class AuthenticationInfo {
  
  private final AuthenticationType type;
  private final String user;
  private final String password;

  public AuthenticationInfo( AuthenticationType type, String user, String password ) {
    this.type = type;
    this.user = user;
    this.password = password;
  }

  public AuthenticationType getType() {
    return type;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }
}
