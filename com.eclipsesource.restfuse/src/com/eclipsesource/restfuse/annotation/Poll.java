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
package com.eclipsesource.restfuse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.PollState;
import com.eclipsesource.restfuse.Response;


/**
 * <p>The <code>Poll</code> annotation can be used for asynchronous HTTP tests that need to poll 
 * the service more than one times. 
 * 
 * <p>Please note, that the <code>Poll</code> annotation only works in combination with the 
 * <code>{@link HttpTest}</code> annotation. This means it <b>can't</b> be used standalone and it 
 * has the same prerequisites as the <code>{@link HttpTest}</code>.</p>
 * 
 * <p>By annotating a test method with <code>Poll</code> you tell the framework to send the request
 * defined in the <code>HttpTest</code> more than one times. The amount of requests can be 
 * configured via the <code>times</code> attribute.</p>
 * 
 * <p>Once a single request has finished on an HTTP test method and the method has a 
 * <code>Poll</code> annotation attached, a <code>{@link PollState} object will be injected into the
 * Test object. The <code>{@link PollState} field needs to be annotated with the 
 * <code>{@link Context}</code> annotation and can be used to get a response for a specific 
 * request during a poll series. The last response will be also injected when a 
 * <code>{@link Context}</code> annotated <code>{@link Response}</code> field is available in the
 * test object. After the injection, the test method will be executed.</p>
 * 
 * <p>A simple poll looks like this:
 * <pre>
 * <b>&#064;RunWith( HttpJUnitRunner.class )</b>
 * public class Example {
 * 
 *   <b>&#064;Rule</b>
 *   public Destination destination = new Destination( "http://localhost" );
 *    
 *   <b>&#064;Context</b>
 *   private PollState pollState;
 *   
 *   <b>&#064;Context</b>
 *   private Response response;
 *   
 *   <b>&#064;HttpTest( method = Method.GET, path = "/test" )</b>
 *   <b>&#064;Poll( times = 5, interval = 500 )</b>
 *   public void testMethod() {
 *     Response currentResponse = pollState.getResponse( pollState.getTimes() );
 *     com.eclipsesource.restfuse.Assert.assertAccepted( currentResponse );
 *     assertEquals( currentResponse, response );
 *   }
 * }
 * </pre>
 * </p>
 * 
 * @see PollState
 * @see HttpTest
 * @see Destination
 * @see Context
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface Poll {

  /**
   * <p>The <code>times</code> attribute specifies the amount of requests that needs to be send 
   * before JUnit will continue with the next test method.</p>
   */
  int times();

  /**
   * <p>The <code>interval</code> attribute specifies the idle time in milliseconds between two 
   * requests in a poll series.</p>
   */
  int interval();
}
