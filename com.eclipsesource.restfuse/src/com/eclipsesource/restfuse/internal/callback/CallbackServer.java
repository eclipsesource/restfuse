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
package com.eclipsesource.restfuse.internal.callback;

import java.lang.reflect.Constructor;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import com.eclipsesource.restfuse.CallbackResource;
import com.eclipsesource.restfuse.annotation.Callback;


public class CallbackServer {

  private CallbackResource resource;
  private int timeout;
  private int port;
  private String path;
  private Server server;
  private CallbackSerlvet servlet;
  private final CallbackStatement statement;

  public CallbackServer( Callback callbackAnnotation, Object target, CallbackStatement callbackStatement ) {
    createResource( callbackAnnotation.resource(), target );
    this.statement = callbackStatement;
    timeout = callbackAnnotation.timeout();
    port = callbackAnnotation.port();
    path = callbackAnnotation.path();
  }

  private void createResource( Class<? extends CallbackResource> type, Object target ) {
    Thread currentThread = Thread.currentThread();
    ClassLoader originalClassLoader = currentThread.getContextClassLoader();
    try {
      currentThread.setContextClassLoader( target.getClass().getClassLoader() );
      doCreateResource( type, target ); 
    } finally {
      currentThread.setContextClassLoader( originalClassLoader );
    }
  }

  private void doCreateResource( Class<? extends CallbackResource> type, Object target ) {
    try {
      Constructor<? extends CallbackResource> constructor 
        = type.getDeclaredConstructor( new Class[] { target.getClass() } );
      constructor.setAccessible( true );
      resource = constructor.newInstance( new Object[] { target } );
    } catch( Exception e ) {
      try {
        Constructor<? extends CallbackResource> constructor = type.getDeclaredConstructor();
        constructor.setAccessible( true );
        resource = constructor.newInstance();
      } catch( Exception shouldNotHappen ) {
        throw new IllegalStateException( "Could not create resource instance of type " 
            + type.getName() + ". Is there an default constructor?", shouldNotHappen );
      }
    }
  }

  public void start() {
    configureServer();
    doStartServer();
  }

  private void configureServer() {
    server = new Server( port );
    Context context = new Context( server, "/", Context.SESSIONS );
    servlet = new CallbackSerlvet( resource, statement );
    context.addServlet( new ServletHolder( servlet ), path );
  }

  private void doStartServer() {
    try {
      server.start();
    } catch( Exception shouldNotHappen ) {
      throw new IllegalStateException( "Could not start Http Server", shouldNotHappen );
    }
  }

  public void stop() {
    try {
      server.stop();
    } catch( Exception shouldNotHappen ) {
      throw new IllegalStateException( "Could not stop Http Server", shouldNotHappen );
    }
  }

  public boolean wasCalled() {
    return servlet.wasCalled();
  }

  public int getTimeout() {
    return timeout;
  }
}
