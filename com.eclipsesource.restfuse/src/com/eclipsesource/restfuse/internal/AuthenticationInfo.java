/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Holger Staudacher - initial API and implementation
 ******************************************************************************/ 
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
