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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;

import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.Status;
import com.eclipsesource.restfuse.annotations.Context;
import com.eclipsesource.restfuse.annotations.HttpTest;


public class HttpTestStatement_Test {
  
  private static final int TIMEOUT = 10;

  private static Server server;

  @Rule
  public Destination destination = new Destination( "http://localhost:10042/test" );
  
  @Context
  private Response response;

  
  @BeforeClass
  public static void setUp() throws Exception {
    server = new Server( 10042 );
    
    org.mortbay.jetty.servlet.Context context 
      = new org.mortbay.jetty.servlet.Context( server, "/", org.mortbay.jetty.servlet.Context.SESSIONS );
    HttpTestStatement statement = mock( HttpTestStatement.class );
    CallbackSerlvet servlet = new CallbackSerlvet( new DefaultCallbackResource(), statement );
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
  
  @Test
  public void testResponseIsNull() {
    HttpTestStatement statement = new HttpTestStatement( null, null, null, null );
    
    assertNull( statement.getResponse() );
  }
  
  @Test
  @HttpTest( method = Method.GET, path = "/" ) 
  public void testSendsRequest() {
    assertEquals( Status.NO_CONTENT.getStatusCode(), response.getStatus() );
    assertNotNull( destination.getResponse() );
  }
  
  @Test
  @HttpTest( method = Method.GET, path = "/" ) 
  public void testInjectsResponse() {
    assertNotNull( response );
    assertEquals( Status.NO_CONTENT.getStatusCode(), response.getStatus() );
  }
}
