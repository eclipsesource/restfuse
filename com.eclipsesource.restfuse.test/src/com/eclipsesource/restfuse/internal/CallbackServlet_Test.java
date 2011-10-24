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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.eclipsesource.restfuse.CallbackResource;
import com.eclipsesource.restfuse.Request;
import com.eclipsesource.restfuse.Response;


@RunWith( MockitoJUnitRunner.class )
public class CallbackServlet_Test {
  
  private CallbackSerlvet callbackSerlvet;
  @Mock
  private CallbackResource resource;
  @Mock
  private HttpTestStatement statement;

  @Before
  public void setUp() {
    callbackSerlvet = new CallbackSerlvet( resource, statement );
    mockResponse();
  }
  
  @Test
  public void testWasCalledIsFalse() {
    assertFalse( callbackSerlvet.wasCalled() );
  }
  
  @Test
  public void testDelegatesGet() throws ServletException, IOException {
    HttpServletRequest req = mockHttpRequest();
    HttpServletResponse resp = mock( HttpServletResponse.class );
    
    callbackSerlvet.doGet( req, resp );
    
    verify( resource ).get( any( Request.class ) );
    assertTrue( callbackSerlvet.wasCalled() );
  }
  
  @Test
  public void testDelegatesPostt() throws ServletException, IOException {
    HttpServletRequest req = mockHttpRequest();
    HttpServletResponse resp = mock( HttpServletResponse.class );
    
    callbackSerlvet.doPost( req, resp );
    
    verify( resource ).post( any( Request.class ) );
    assertTrue( callbackSerlvet.wasCalled() );
  }
  
  @Test
  public void testDelegatesDelete() throws ServletException, IOException {
    HttpServletRequest req = mockHttpRequest();
    HttpServletResponse resp = mock( HttpServletResponse.class );
    
    callbackSerlvet.doDelete( req, resp );
    
    verify( resource ).delete( any( Request.class ) );
    assertTrue( callbackSerlvet.wasCalled() );
  }
  
  @Test
  public void testDelegatesPut() throws ServletException, IOException {
    HttpServletRequest req = mockHttpRequest();
    HttpServletResponse resp = mock( HttpServletResponse.class );
    
    callbackSerlvet.doPut( req, resp );
    
    verify( resource ).put( any( Request.class ) );
    assertTrue( callbackSerlvet.wasCalled() );
  }
  
  @Test
  public void testDelegatesOptions() throws ServletException, IOException {
    HttpServletRequest req = mockHttpRequest();
    HttpServletResponse resp = mock( HttpServletResponse.class );
    
    callbackSerlvet.doOptions( req, resp );
    
    verify( resource ).options( any( Request.class ) );
    assertTrue( callbackSerlvet.wasCalled() );
  }
  
  @Test
  public void testDelegatesHead() throws ServletException, IOException {
    HttpServletRequest req = mockHttpRequest();
    HttpServletResponse resp = mock( HttpServletResponse.class );
    
    callbackSerlvet.doHead( req, resp );
    
    verify( resource ).head( any( Request.class ) );
    assertTrue( callbackSerlvet.wasCalled() );
  }
  
  @Test
  public void testDelegatesFailure() throws ServletException, IOException {
    when( resource.get( any( Request.class ) ) ).thenThrow( new IllegalStateException() );
    HttpServletRequest req = mockHttpRequest();
    HttpServletResponse resp = mock( HttpServletResponse.class );
    
    callbackSerlvet.doGet( req, resp );
    
    verify( statement ).failWithinCallback( any( Throwable.class ) );
  }

  private void mockResponse() {
    Response response = mock( Response.class );
    when( resource.get( any( Request.class ) ) ).thenReturn( response );
    when( resource.post( any( Request.class ) ) ).thenReturn( response );
    when( resource.delete( any( Request.class ) ) ).thenReturn( response );
    when( resource.put( any( Request.class ) ) ).thenReturn( response );
    when( resource.head( any( Request.class ) ) ).thenReturn( response );
    when( resource.options( any( Request.class ) ) ).thenReturn( response );
  }
  
  private HttpServletRequest mockHttpRequest() throws IOException {
    HttpServletRequest req = mock( HttpServletRequest.class );
    when( req.getReader() ).thenReturn( new BufferedReader( new StringReader( "test" ) ) );
    return req;
  }
}
