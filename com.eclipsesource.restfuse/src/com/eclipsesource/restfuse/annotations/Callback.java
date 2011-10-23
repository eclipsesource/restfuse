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

import com.eclipsesource.restfuse.CallbackResource;
import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.Destination;


/**
 * <p>The <code>Callback</code> can be used for asynchronous http tests. This means when the tested
 * service needs to send something back within a separate request. At this time the client needs to
 * act as a service to accept the incoming request. See 
 * <a href="http://wiki.webhooks.org/w/page/13385124/FrontPage">this wiki</a> for more information 
 * about webhooks.</p>
 * 
 * <p>Please note, that the <code>Callback</code> annotation only works in combination with the 
 * <code>{@link HttpTest}</code> annotation. This means it <b>can't</b> be used standalone and it 
 * has the same prerequisites as the <code>{@link HttpTest}</code>.</p>
 * 
 * <p>Once a request has finished on an http test method and the method has a <code>Callback</code>
 * annotation attached, a sever will be started before the test code will be executed. On this 
 * server a servlet will be registered on the port specified in the <code>port</code> attribute 
 * using the path specified in the <code>path</code> attribute. After the test code finished the 
 * server waits until the <code>timeout</code> has reached or the callback was called. If the 
 * callback was not done than the test method fails. In any case the server shuts itself down after 
 * the test method execution.</p>
 * 
 * <p>Of course you want to test something when the callback arrives. Therefore you need to register
 * a resource using the <code>resource</code> attribute. The value of this attributes has to be a 
 * subclass of <code>{@link CallbackResource}</code>. For convenience you can also subclass the 
 * <code>{@link DefaultCallbackResource}</code> which implements all methods and returns a default 
 * response instead of sub classing the <code>{@link CallbackResource}</code> directly.</p>
 * 
 * <p><b>PLEASE NOTE:</b> The value of the <code>resource</code> attribute has to be an inner class
 * of the TestCase at the moment. This may change in the future.</p>
 * 
 * <p>A simple callback looks like this:
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
 *   private class TestCallbackResource extends DefaultCallbackResource {
 *    
 *     <b>&#064;Override</b>
 *     public Response post( Request request ) {
 *       assertNotNull( request.getHeaders().get( "some header" ).get( 0 ) );
 *       return super.post( request );
 *     }
 *   }
 * 
 *   <b>&#064;HttpTest( method = Method.GET, path = "/test" )</b>
 *   <b>&#064;Callback( port = 9090, path = "/callback", resource = TestCallbackResource.class, timeout = 10000 )</b>
 *   public void testMethod() {
 *     com.eclipsesource.restfuse.Assert.assertAccepted( response );
 *   }
 * }
 * </pre>
 * </p>
 * 
 * @see CallbackResource
 * @see DefaultCallbackResource
 * @see HttpTest
 * @see Destination
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface Callback {

  /**
   * <p>The <code>port</code> attribute specifies the port on which the callback will be reachable 
   * during the test method execution.</p>
   */
  int port();

  /**
   * <p>The <code>path</code> attribute specifies the path on which the callback will be reachable 
   * during the test method execution.</p>
   */  
  String path();

  /**
   * <p>The <code>resource</code> attribute specifies the class that will be instantiated and 
   * handles the incoming requests from a callback. Instead of sub classing 
   * <code>{@link CallbackResource}</code> directly you can also subclass 
   * <code>{@link DefaultCallbackResource}</code> which implements all methods and returns a 
   * default response.</p> 
   * 
   * <p><b>PLEASE NOTE:</b> The value of the <code>resource</code> attribute has to be an inner 
   * class of the TestCase at the moment. This may change in the future.</p>
   * 
   * @see CallbackResource
   * @see DefaultCallbackResource
   */ 
  Class<? extends CallbackResource> resource();

  /**
   * <p>The <code>timeout</code> attribute specifies the milliseconds of the callback timeout. This
   * means, that if the callback was not called within the given time frame, the test method will 
   * fail.</p>
   */
  int timeout();
}
