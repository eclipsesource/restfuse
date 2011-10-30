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

import static org.junit.Assert.fail;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Callback;
import com.eclipsesource.restfuse.internal.HttpTestStatement;


public class CallbackStatement extends Statement {
  
  private static final int WAIT_TIME = 100;
  
  private HttpTestStatement base;
  private FrameworkMethod method;
  private Object target;
  private CallbackServer callbackServer;
  private final Object lock = new Object();
  private volatile String errorMessage;

  private final Statement statement;

  public CallbackStatement( Statement statement,
                            HttpTestStatement base, 
                            FrameworkMethod method, 
                            Object target ) 
  {
    this.statement = statement;
    this.base = base;
    this.method = method;
    this.target = target;
  }

  @Override
  public void evaluate() throws Throwable {
    try {
      startCallbackServerWhenAvailable();
      Response response = base.sendRequest();
      base.tryInjectResponse( response );
      statement.evaluate();
    } finally {
      waitForCallbackWhenAvailable();
    }
  }
  
  private void startCallbackServerWhenAvailable() {
    Callback callbackAnnotation = method.getAnnotation( Callback.class );
    if( callbackAnnotation != null ) {
      callbackServer = new CallbackServer( callbackAnnotation, target, this );
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
        checkForFailuresDuringCallback();
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

  private void checkForFailuresDuringCallback() {
    synchronized( lock ) {
      if( errorMessage != null ) {
        fail( errorMessage );
      }
    }
  }

  private void checkCallbackWasCalled() {
    if( !callbackServer.wasCalled() ) {
      fail( "Callback was not called" );
    }
  }
  
  public void failWithinCallback( Throwable cause ) {
    synchronized( lock ) {
      errorMessage = cause.getMessage();
    }
  }
}
