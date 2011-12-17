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
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;

import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.Status;
import com.eclipsesource.restfuse.annotation.Authentication;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.Header;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.internal.callback.CallbackSerlvet;
import com.eclipsesource.restfuse.internal.callback.CallbackStatement;


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
    CallbackStatement statement = mock( CallbackStatement.class );
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
    int timer = 0;
    while( !server.isStopped() && timer < TIMEOUT ) {
      Thread.sleep( 1000 );
      timer++;
    }
  }
  
  @Test
  @HttpTest( method = Method.GET, path = "/" ) 
  public void testInjectsResponse() {
    assertNotNull( response );
    assertEquals( Status.NO_CONTENT.getStatusCode(), response.getStatus() );
  }
  
  @Test
  public void testRemovesProxyProperties() throws Throwable {
    Statement base = mock( Statement.class );
    FrameworkMethod method = mock( FrameworkMethod.class );
    HttpTest annotation = createAnnotation();
    when( method.getAnnotation( HttpTest.class ) ).thenReturn( annotation );
    Object target = new Object();
    HttpTestStatement statement 
      = new HttpTestStatement( base, method, target, "http://localhost", "http://proxy.com", 8080 );
    
    statement.evaluate();
    
    assertNull( System.getProperty( HttpTestStatement.HTTP_PROXY_HOST ) );
    assertNull( System.getProperty( HttpTestStatement.HTTP_PROXY_PORT ) );
  }

  private HttpTest createAnnotation() {
    HttpTest annotation = new HttpTest() {
      
      @Override
      public Class<? extends Annotation> annotationType() {
        return null;
      }
      
      @Override
      public MediaType type() {
        return MediaType.APPLICATION_JSON;
      }
      
      @Override
      public String path() {
        return "/";
      }
      
      @Override
      public Method method() {
        return Method.GET;
      }
      
      @Override
      public Header[] headers() {
        return null;
      }
      
      @Override
      public String file() {
        return "";
      }
      
      @Override
      public String content() {
        return "content";
      }
      
      @Override
      public Authentication[] authentications() {
        return null;
      }
    };
    return annotation;
  }
}
