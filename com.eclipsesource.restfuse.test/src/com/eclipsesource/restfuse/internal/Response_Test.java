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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.eclipsesource.restfuse.MediaType;
import com.github.kevinsawicki.http.HttpRequest;


public class Response_Test {
  
  private ResponseImpl response;

  @Before
  public void setUp() throws MalformedURLException {
    HttpRequest httpRequest = mockRequest();
    response = new ResponseImpl( httpRequest );
  }

  private HttpRequest mockRequest() throws MalformedURLException {
    HttpRequest httpRequest = mock( HttpRequest.class );
    HttpURLConnection connection = mock( HttpURLConnection.class );
    when( connection.getURL() ).thenReturn( new URL( "http://test.com" ) );
    when( httpRequest.getConnection() ).thenReturn( connection );
    when( httpRequest.body() ).thenReturn( "test" );
    when( httpRequest.code() ).thenReturn( 200 );
    when( httpRequest.contentType() ).thenReturn( MediaType.TEXT_PLAIN.toString() );
    HashMap<String, List<String>> headers = new HashMap<String, List<String>>();
    List<String> values = new ArrayList<String>();
    values.add( "test" );
    headers.put( "test", values );
    when( httpRequest.headers() ).thenReturn( headers );
    return httpRequest;
  }
  
  @Test
  public void testResponseCode() {
    assertEquals( 200, response.getStatus() );
  }
  
  @Test
  public void testHasBody() {
    assertTrue( response.hasBody() );
  }
  
  @Test
  public void testGetBody() {
    assertEquals( "test", response.getBody() );
  }
  
  @Test
  public void testGetMediaType() {
    assertEquals( MediaType.TEXT_PLAIN, response.getType() );
  }
  
  @Test
  public void testGetHeaders() {
    assertEquals( "test", response.getHeaders().get( "test" ).get( 0 ) );
  }
  
  @Test
  public void testGetUrl() {
    assertEquals( "http://test.com", response.getUrl() );
  }
}
