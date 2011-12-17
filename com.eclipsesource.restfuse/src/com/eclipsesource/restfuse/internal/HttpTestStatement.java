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

import java.lang.reflect.Field;
import java.util.Properties;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Callback;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.annotation.Poll;
import com.eclipsesource.restfuse.internal.callback.CallbackStatement;
import com.eclipsesource.restfuse.internal.poll.PollStatement;
import com.sun.jersey.api.client.ClientResponse;


public class HttpTestStatement extends Statement {

  static final String HTTP_PROXY_HOST = "http.proxyHost";
  static final String HTTP_PROXY_PORT = "http.proxyPort";

  private final Statement base;
  private final FrameworkMethod method;
  private final Object target;
  private final String baseUrl;
  private final String proxyHost;
  private final int proxyPort;
  
  public HttpTestStatement( Statement base, 
                            FrameworkMethod method, 
                            Object target, 
                            String baseUrl, 
                            String proxyHost, 
                            int proxyPort ) 
  {
    this.base = base;
    this.method = method;
    this.target = target;
    this.baseUrl = baseUrl;
    this.proxyHost = proxyHost;
    this.proxyPort = proxyPort;
  }

  @Override
  public void evaluate() throws Throwable {
    setProxyProperties();
    try {
      doEvaluate();
    } finally {
      unsetProxyProperties();
    }
  }

  private void setProxyProperties() {
    if( proxyHost != null ) {
      System.setProperty( HTTP_PROXY_HOST, proxyHost );
      System.setProperty( HTTP_PROXY_PORT, String.valueOf( proxyPort ) );
    }
  }

  private void doEvaluate() throws Throwable {
    Statement delegate = new BasicStatement( base, this );
    if( needsCallback() ) {
      delegate = new CallbackStatement( base, this, method, target );
    } else if( needsPoll() ) {
      delegate = new PollStatement( base, this, method, target );
    } 
    delegate.evaluate();
  }

  private void unsetProxyProperties() {
    Properties properties = System.getProperties();
    properties.remove( HTTP_PROXY_HOST );
    properties.remove( HTTP_PROXY_PORT );
  }

  private boolean needsCallback() {
    Callback callbackAnnotation = method.getAnnotation( Callback.class );
    return callbackAnnotation != null;
  }

  private boolean needsPoll() {
    Poll pollAnnotation = method.getAnnotation( Poll.class );
    return pollAnnotation != null;
  }

  public Response sendRequest() {
    InternalRequest request = buildRequest();
    ClientResponse clientResponse = callService( request );
    return new ResponseImpl( clientResponse );
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

  public void tryInjectResponse( Response response ) {
    Field[] fields = target.getClass().getDeclaredFields();
    for( Field field : fields ) {
      Context contextAnnotation = field.getAnnotation( Context.class );
      if( contextAnnotation != null && field.getType() == Response.class ) {
        injectResponse( field, response );
      }
    }
  }

  private void injectResponse( Field field, Response response ) {
    field.setAccessible( true );
    try {
      field.set( target, response );
    } catch( Exception exception ) {
      throw new IllegalStateException( "Could not inject response.", exception );
    }
  }

}
