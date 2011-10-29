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
package com.eclipsesource.restfuse;

import static com.eclipsesource.restfuse.Assert.assertNoContent;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;

import com.eclipsesource.restfuse.annotations.Context;
import com.eclipsesource.restfuse.annotations.HttpTest;
import com.eclipsesource.restfuse.annotations.Poll;
import com.eclipsesource.restfuse.internal.CallbackSerlvet;
import com.eclipsesource.restfuse.internal.HttpTestStatement;


public class Poll_Test {
  
  private static final int TIMEOUT = 10;
  private static Server server;
  private static int COUNT = 0;
  
  @Rule
  public Destination destination = new Destination( "http://localhost:10044/test" );
  
  @Context
  private PollState pollState;
  
  private static class TestResource extends DefaultCallbackResource {
    @Override
    public Response get( Request request ) {
      COUNT++;
      return super.get( request );
    }
  }

  @BeforeClass
  public static void setUp() throws Exception {
    server = new Server( 10044 );
    
    org.mortbay.jetty.servlet.Context context 
      = new org.mortbay.jetty.servlet.Context( server, "/", org.mortbay.jetty.servlet.Context.SESSIONS );
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
  
  @Test
  @HttpTest( method = Method.GET, path = "/" ) 
  @Poll( times = 5, interval = 500 )
  public void testSendsRequest() {
    assertNoContent( pollState.getResponse( pollState.getTimes() ) );
    assertEquals( pollState.getTimes(), COUNT );
  }
}
