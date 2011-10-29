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

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import com.eclipsesource.restfuse.AuthenticationType;
import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Request;
import com.eclipsesource.restfuse.Response;


public class InternalRequest_Test {
  
  private static final int TIMEOUT = 10;

  private static Server server;
  
  @BeforeClass
  public static void setUp() throws Exception {
    server = new Server( 10042 );
    Context context = new Context( server, "/", Context.SESSIONS );
    HttpTestStatement statement = mock( HttpTestStatement.class );
    CallbackSerlvet servlet = new CallbackSerlvet( new TestResource(), statement );
    context.addServlet( new ServletHolder( servlet ), "/" );
    server.start();
    int timer = 0;
    while( !server.isRunning() && timer < TIMEOUT ) {
      Thread.sleep( 1000 );
      timer++;
    }
  }
  
  @AfterClass
  public static void tearDown() throws Exception {
    server.stop();
    int timer = 0;
    while( !server.isStopped() && timer < TIMEOUT ) {
      Thread.sleep( 1000 );
      timer++;
    }
  }
  
  private static class TestResource extends DefaultCallbackResource {
    @Override
    public Response get( Request request ) {
      Map<String, List<String>> headers = request.getHeaders();
      assertTrue( headers.containsKey( "Authorization" ) );
      assertEquals( "test", headers.get( "test" ).get( 0 ) );
      assertEquals( MediaType.TEXT_PLAIN, request.getType() );
      return super.post( request );
    }
    
    @Override
    public Response post( Request request ) {
      assertEquals( "test", request.getBody() );
      Map<String, List<String>> headers = request.getHeaders();
      assertTrue( headers.containsKey( "Authorization" ) );
      assertEquals( "test", headers.get( "test" ).get( 0 ) );
      assertEquals( MediaType.TEXT_PLAIN, request.getType() );
      return super.post( request );
    }
    
    @Override
    public Response put( Request request ) {
      assertEquals( "test", request.getBody() );
      Map<String, List<String>> headers = request.getHeaders();
      assertTrue( headers.containsKey( "Authorization" ) );
      assertEquals( "test", headers.get( "test" ).get( 0 ) );
      assertEquals( MediaType.TEXT_PLAIN, request.getType() );
      return super.post( request );
    }
    
    @Override
    public Response delete( Request request ) {
      Map<String, List<String>> headers = request.getHeaders();
      assertTrue( headers.containsKey( "Authorization" ) );
      assertEquals( "test", headers.get( "test" ).get( 0 ) );
      assertEquals( MediaType.TEXT_PLAIN, request.getType() );
      return super.post( request );
    }
    
    @Override
    public Response options( Request request ) {
      Map<String, List<String>> headers = request.getHeaders();
      assertTrue( headers.containsKey( "Authorization" ) );
      assertEquals( "test", headers.get( "test" ).get( 0 ) );
      assertEquals( MediaType.TEXT_PLAIN, request.getType() );
      return super.post( request );
    }
    
    @Override
    public Response head( Request request ) {
      Map<String, List<String>> headers = request.getHeaders();
      assertTrue( headers.containsKey( "Authorization" ) );
      assertEquals( "test", headers.get( "test" ).get( 0 ) );
      assertEquals( MediaType.TEXT_PLAIN, request.getType() );
      return super.post( request );
    }
    
  }
  
  @Test
  public void testTransmitsGetData() throws UnsupportedEncodingException {
    InternalRequest request = createRequest();
    
    request.get();
  }
  
  @Test
  public void testTransmitsPostData() throws UnsupportedEncodingException {
    InternalRequest request = createRequest();
    
    request.post();
  }
  
  @Test
  public void testTransmitsPutData() throws UnsupportedEncodingException {
    InternalRequest request = createRequest();
    
    request.put();
  }
  
  @Test
  public void testTransmitsDeleteData() throws UnsupportedEncodingException {
    InternalRequest request = createRequest();
    
    request.delete();
  }
  
  @Test
  public void testTransmitsOptionsData() throws UnsupportedEncodingException {
    InternalRequest request = createRequest();
    
    request.options();
  }
  
  @Test
  public void testTransmitsHeadData() throws UnsupportedEncodingException {
    InternalRequest request = createRequest();
    
    request.head();
  }

  private InternalRequest createRequest() throws UnsupportedEncodingException {
    InternalRequest internalRequest = new InternalRequest( "http://localhost:10042/test" );
    internalRequest.setContent( new ByteArrayInputStream( "test".getBytes( "UTF-8" ) ) );
    internalRequest.setContentType( MediaType.TEXT_PLAIN.getMimeType() );
    internalRequest.addHeader( "test", "test" );
    AuthenticationInfo authentication 
      = new AuthenticationInfo( AuthenticationType.BASIC, "test", "test" );
    internalRequest.addAuthenticationInfo( authentication );
    return internalRequest;
  }
}
