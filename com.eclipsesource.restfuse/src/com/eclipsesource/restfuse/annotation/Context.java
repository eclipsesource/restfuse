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
 * The <code>Context</code> annotation can be used within a TestCase to get the 
 * <code>{@link Response}</code> or a <code>{@link PollState}</code> of an
 * HTTP request injected after it succeeds.</p>
 * 
 * <p>A simple example looks like this:
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
 *     com.eclipsesource.restfuse.Assert.assertAccepted( <b>response</b> );
 *   }
 * }
 * </pre>
 * </p>
 * 
 * @see HttpTest
 * @see Destination
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD } )
public @interface Context {
  // no content
}
