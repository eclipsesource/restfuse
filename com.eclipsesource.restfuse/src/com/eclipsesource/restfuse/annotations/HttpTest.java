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
package com.eclipsesource.restfuse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eclipsesource.restfuse.AuthenticationType;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.internal.AuthenticationInfo;


/**
 * <p>The <code>HttpTest</code> annotation tells JUnit that the <code>public void</code> method
 * to which it is attached can be run as an http test case when it's coupled with a <code>Test</code> 
 * annotation. Before the test method will be executed an request will be send to the service
 * configured via the <code>Destination</code> rule and the annotation's parameters.</p>
 * <p>Please keep in mind that an <code>HttpTest</code> only works when a 
 * <code>{@link Destination}</code> Rule exists in the TestCase.</p>
 * <p>If the request fails, the test fails. When the request succeeds, the <code>Response</code> 
 * will be injected into the TestCase Object. The injection looks for a field of the type 
 * <code>{@link Response}</code> which is annotated with the <code>{@link Context}</code> 
 * annotation. The response object can also be pulled using the <code>getRespone()</code> method 
 * on the <code>{@link Destination}</code> rule.</p>
 * <p>You don't have to combine the <code>{@link org.junit.Test}</code> and <code>HttpTest</code> 
 * annotation when you are using the <code>{@link HttpJUnitRunner}</code>. This runner detects all 
 * <code>HttpTest</code> annotated methods and executes them as test methods.</p> 
 * 
 * <p>To run the method, JUnit first constructs a fresh instance of the class. Then the request will 
 * be executed and JUnit invokes the annotated method afterwards. Any exceptions thrown by the 
 * test will be reported by JUnit as a failure. If no exceptions are thrown, the test is assumed
 * to have succeeded.</p>
 * 
 * <p>A simple http test looks like this:
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
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface HttpTest {

  /**
   * <p>The method attribute tells the request which Http Method should be used.</p>
   * 
   * @see Method
   */
  Method method();

  /**
   * <p>The path attribute will be attached to the base url of the <code>{@link Destination}</code> 
   * rule. This enables the creation of many http test method within one TestCase using different 
   * url paths for the same host.</p>
   */
  String path();

  /**
   * <p>The type attribute tells the request which "Content-Type" header it should send. The default
   * value is the wildcard type.</p>
   * 
   * @see MediaType
   */
  MediaType type() default MediaType.WILDCARD;

  /**
   * <p>When the request can have an entity like POST or PUT requests the content can be set by the 
   * <code>file</code> attribute. The file has to be on the classpath of the TestCase. An 
   * alternative can be to use the <code>content</code> attribute. If both are defined the 
   * <code>file</code> attribute wins.</p>
   */
  String file() default EMPTY;

  /**
   * <p>When the request can have an entity like POST or PUT requests the content can be set by the 
   * <code>content</code> attribute. An alternative can be to use the <code>file</code> attribute. 
   * If both are defined the <code>file</code> attribute wins.</p>
   */
  String content() default EMPTY;

  /**
   * <p>With the <code>headers</code> the request headers can be set.</p>
   * 
   * @see Header
   */
  Header[] headers() default {};

  /**
   * <strong>PROVISIONAL API, MAY CHANGE OR VANISH IN FUTURE</strong>
   * <p>The <code>{@link AuthenticationInfo}</code> will be used to transmit authentication data. 
   * Currently only BASIC and DIGEST authentication is supported.</p>
   * 
   * @see AuthenticationInfo
   * @see AuthenticationType
   */
  Authentication[] authentications() default {};
  
  static final String EMPTY = "";

}
