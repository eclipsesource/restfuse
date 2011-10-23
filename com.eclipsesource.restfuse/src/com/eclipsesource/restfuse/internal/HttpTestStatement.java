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

import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotations.Callback;
import com.eclipsesource.restfuse.annotations.Context;
import com.eclipsesource.restfuse.annotations.HttpTest;
import com.sun.jersey.api.client.ClientResponse;


public class HttpTestStatement extends Statement {

  private static final int WAIT_TIME = 100;
  
  private final Statement base;
  private final FrameworkMethod method;
  private final Object target;
  private final String baseUrl;
  private Response response;
  private CallbackServer callbackServer;

  public HttpTestStatement( Statement base, 
                            FrameworkMethod method, 
                            Object target, 
                            String baseUrl ) 
  {
    this.base = base;
    this.method = method;
    this.target = target;
    this.baseUrl = baseUrl;
  }

  public Response getResponse() {
    return response;
  }

  @Override
  public void evaluate() throws Throwable {
    try {
      startCallbackServerWhenAvailable();
      sendRequest();
      tryInjectResponse();
      base.evaluate();
    } finally {
      waitForCallbackWhenAvailable();
    }
  }

  private void startCallbackServerWhenAvailable() {
    Callback callbackAnnotation = method.getAnnotation( Callback.class );
    if( callbackAnnotation != null ) {
      callbackServer = new CallbackServer( callbackAnnotation, target );
      callbackServer.start();
    }
  }

  private void waitForCallbackWhenAvailable() {
    if( callbackServer != null ) {
      try {
        int waitTime = 0;
        while( !callbackServer.wasCalled() && waitTime <= callbackServer.getTimeout() ) {
          sleep();
          waitTime += WAIT_TIME;
        }
      checkCallbackWasCalled();
      } finally {
        callbackServer.stop();
      }
    }
  }

  private void sleep() {
    try {
      Thread.sleep( WAIT_TIME );
    } catch( InterruptedException shouldNotHappen ) {
      throw new IllegalStateException( "Could not wait until callback was called", 
                                       shouldNotHappen );
    }
  }

  private void checkCallbackWasCalled() {
    if( !callbackServer.wasCalled() ) {
      fail( "Callback was not called" );
    }
  }

  private void sendRequest() {
    InternalRequest request = buildRequest();
    ClientResponse clientResponse = callService( request );
    response = new ResponseImpl( clientResponse );
  }

  private InternalRequest buildRequest() {
    RequestConfiguration requestConfiguration = new RequestConfiguration( baseUrl, method, target );
    return requestConfiguration.createRequest();
  }

  private ClientResponse callService( InternalRequest request ) {
    Method requestMethod = method.getAnnotation( HttpTest.class ).method();
    ClientResponse result = null;
    if( requestMethod.equals( Method.GET ) ) {
      result = request.get();
    } else if( requestMethod.equals( Method.POST ) ) {
      result = request.post();
    } else if( requestMethod.equals( Method.DELETE ) ) {
      result = request.delete();
    } else if( requestMethod.equals( Method.PUT ) ) {
      result = request.put();
    } else if( requestMethod.equals( Method.HEAD ) ) {
      result = request.head();
    } else if( requestMethod.equals( Method.OPTIONS ) ) {
      result = request.options();
    }
    return result;
  }

  private void tryInjectResponse() {
    Field[] fields = target.getClass().getDeclaredFields();
    for( Field field : fields ) {
      Context responseAnnotation = field.getAnnotation( Context.class );
      if( responseAnnotation != null && field.getType() == Response.class ) {
        injectResponse( field );
      }
    }
  }

  private void injectResponse( Field field ) {
    field.setAccessible( true );
    try {
      field.set( target, response );
    } catch( Exception exception ) {
      throw new IllegalStateException( "Could not inject response.", exception );
    }
  }

}
