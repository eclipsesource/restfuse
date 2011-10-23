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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.eclipsesource.restfuse.MediaType;


public class RequestImpl_Test {
  
  private RequestImpl request;

  @Before
  public void setUp() {
    request = new RequestImpl( "test", MediaType.APPLICATION_ATOM_XML );
    request.addHeader( "test", "test1" );
    request.addHeader( "test", "test2" );
    request.addHeader( "test2", "test" );
  }
  
  @Test
  public void testHasBody() {
    RequestImpl noBodyRequest = new RequestImpl( null, MediaType.WILDCARD );
    
    assertFalse( noBodyRequest.hasBody() );
    assertTrue( request.hasBody() );
  }
  
  @Test
  public void testGetBody() {
    assertEquals( "test", request.getBody() );
  }
  
  @Test
  public void testGetMediaType() {
    assertEquals( MediaType.APPLICATION_ATOM_XML, request.getType() );
  }
  
  @Test
  public void testGetHeader() {
    Map<String, List<String>> headers = request.getHeaders();
    
    assertEquals( "test1", headers.get( "test" ).get( 0 ) );
    assertEquals( "test2", headers.get( "test" ).get( 1 ) );
    assertEquals( "test", headers.get( "test2" ).get( 0 ) );
  }
}
