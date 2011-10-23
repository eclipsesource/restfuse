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
