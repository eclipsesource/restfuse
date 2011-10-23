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

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.annotations.Callback;
import com.eclipsesource.restfuse.annotations.Context;
import com.eclipsesource.restfuse.annotations.HttpTest;
import com.eclipsesource.restfuse.internal.HttpTestStatement;

/**
 * <p>A <code>Destination</code> marks a requirement for http tests. Before you can use the 
 * <code>{@link HttpTest}</code> annotation you need to instantiate a <code>Destination</code> in 
 * your TestCase. This rule does manage the <code>{@link HttpTest}</code> and the 
 * <code>{@link Callback}</code> annotations of your test methods.</p>
 * 
 * <p>A <code>Destination</code> needs an url during instantiation. This url will be used within a
 * TestCase to send requests to. If the url is not valid an <code>IllegalArgumentException</code> 
 * will be thrown.</p>
 *
 * <p>A simple test looks like this:
 * <pre>
 * <b>&#064;RunWith( HttpJUnitRunner.class )</b>
 * public class Example {
 * 
 *   <b>&#064;Rule</b>
 *   public Destination destination = new Destination( "http://localhost" );
 *    
 *   <b>&#064;Context</b>
 *   private Response response;
 * 
 *   <b>&#064;HttpTest( method = Method.GET, path = "/test" )</b> 
 *   public void testMethod() {
 *     com.eclipsesource.restfuse.Assert.assertAccepted( response );
 *   }
 * }
 * </pre>
 * </p>
 * 
 * @see HttpTest
 * @see Callback
 */
public class Destination implements MethodRule {

  private HttpTestStatement requestStatement;
  private final String baseUrl;

  /**
   * <p>Constructs a new <code>Destination</code> object. An url is needed as parameter which will
   * be used in the whole test to send requests to.</p>
   * 
   * @param baseUrl The url to send requests to.
   * 
   * @throws IllegalArgumentException Will be thrown when the <code>baseUrl</code> is null or 
   * not a valid url.
   */
  public Destination( String baseUrl ) {
    checkBaseUrl( baseUrl );
    this.baseUrl = baseUrl;
  }

  private void checkBaseUrl( String baseUrl ) {
    if( baseUrl == null ) {
      throw new IllegalArgumentException( "baseUrl must not be null" );
    }
    try {
      new URL( baseUrl );
    } catch( MalformedURLException mue ) {
      throw new IllegalArgumentException( "baseUrl has to be an URL" );
    }
  }

  @Override
  /**
   * <p><b>Not meant for public use. This method will be invoked by the JUnit framework.</b></p>
   */
  public Statement apply( Statement base, FrameworkMethod method, Object target )
  {
    Statement result;
    if( hasAnnotation( method ) ) {
      requestStatement = new HttpTestStatement( base, method, target, baseUrl );
      result = requestStatement;
    } else {
      result = base;
    }
    return result;
  }

  private boolean hasAnnotation( FrameworkMethod method ) {
    return method.getAnnotation( HttpTest.class ) != null;
  }

  /**
   * <p>The <code>getResponse()</code> method can be used within a test method to pull the response
   * instead of injecting them. To inject a <code>{@link Response}</code> the 
   * <code>{@link Context}</code> can be used.</p>
   * 
   * @throws IllegalStateException When this method was called but the request has failed.
   * 
   * @see Context
   */
  public Response getResponse() {
    if( requestStatement == null ) {
      throw new IllegalStateException( "Test method does not contain enough " 
                                       + "information to send the request" );
    }
    return requestStatement.getResponse();
  }
}
