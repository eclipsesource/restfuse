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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;


public class ResponseImpl_Test {
  
  private ResponseImpl response;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() {
    ClientResponse clientResponse = mock( ClientResponse.class );
    when( clientResponse.getEntity( any( Class.class ) ) ).thenReturn( "test" );
    when( clientResponse.hasEntity() ).thenReturn( true );
    when( clientResponse.getType() ).thenReturn( MediaType.APPLICATION_ATOM_XML_TYPE );
    MultivaluedMap<String, String> headers = mockHeaders();
    when( clientResponse.getHeaders() ).thenReturn( headers );
    response = new ResponseImpl( clientResponse );
  }

  @SuppressWarnings("unchecked")
  private MultivaluedMap<String, String> mockHeaders() {
    MultivaluedMap<String, String> headers = mock( MultivaluedMap.class );
    Set<String> keyset = new HashSet<String>();
    keyset.add( "test" );
    when( headers.keySet() ).thenReturn( keyset );
    List<String> list = new ArrayList<String>();
    list.add( "test" );
    when( headers.get( anyString() ) ).thenReturn( list );
    return headers;
  }
  
  @Test
  public void testHasBody() {
    assertTrue( response.hasBody() );
  }
  
  @Test
  public void testGetBody() {
    assertEquals( "test", response.getBody( String.class ) );
  }
  
  @Test
  public void testGetType() {
    assertEquals( com.eclipsesource.restfuse.MediaType.APPLICATION_ATOM_XML, response.getType() );
  }
  
  @Test
  public void testGetHeaders() {
    Map<String, List<String>> headers = response.getHeaders();
    
    assertEquals( 1, headers.size() );
    assertEquals( "test", headers.get( "test" ).get( 0 ) );
  }
  
  
  
  
}
