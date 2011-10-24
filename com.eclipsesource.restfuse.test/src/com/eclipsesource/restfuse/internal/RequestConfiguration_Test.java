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

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import com.eclipsesource.restfuse.AuthenticationType;
import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Request;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotations.Authentication;
import com.eclipsesource.restfuse.annotations.Header;
import com.eclipsesource.restfuse.annotations.HttpTest;


public class RequestConfiguration_Test {
  
  private static final int TIMEOUT = 10;

  private static Server server;
  
  @BeforeClass
  public static void setUp() throws Exception {
    server = new Server( 10043 );
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
  }
  
  private static class TestResource extends DefaultCallbackResource {
    
    @Override
    public Response post( Request request ) {
      assertEquals( "test", request.getBody() );
      Map<String, List<String>> headers = request.getHeaders();
      assertTrue( headers.containsKey( "Authorization" ) );
      assertEquals( "test", headers.get( "test" ).get( 0 ) );
      assertEquals( MediaType.TEXT_PLAIN, request.getType() );
      return super.post( request );
    }
    
  }
  
  @Rule
  public Destination destination = new Destination( "http://localhost:10043/test" );
  
  @Test
  @HttpTest( 
    method = Method.POST, 
    headers = { @Header( name = "test", value = "test" ) }, 
    path = "/",
    authentications = { @Authentication( type = AuthenticationType.BASIC, user = "test", password = "test" ) },
    type = MediaType.TEXT_PLAIN,
    content = "test"
  )
  public void fakeTestMethod() {
    // test is done by resource
  }
  
  
}
