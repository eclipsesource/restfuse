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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

import com.eclipsesource.restfuse.AuthenticationType;
import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Request;
import com.eclipsesource.restfuse.RequestContext;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Authentication;
import com.eclipsesource.restfuse.annotation.Header;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.internal.callback.CallbackSerlvet;
import com.eclipsesource.restfuse.internal.callback.CallbackStatement;

public class RequestConfiguration_Test {

  private static final int TIMEOUT = 10;

  private static Server server;

  private static TestResource resource = new TestResource();

  @BeforeClass
  public static void setUp() throws Exception {
    server = new Server(10043);
    ServletContextHandler context = new ServletContextHandler(server, "/",
        ServletContextHandler.SESSIONS);
    CallbackStatement statement = mock(CallbackStatement.class);
    CallbackSerlvet servlet = new CallbackSerlvet(resource, statement);
    context.addServlet(new ServletHolder(servlet), "/");
    server.start();
    int timer = 0;
    while (!server.isRunning() && timer < TIMEOUT) {
      Thread.sleep(1000);
      timer++;
    }
  }

  @AfterClass
  public static void tearDown() throws Exception {
    server.stop();
    int timer = 0;
    while (!server.isStopped() && timer < TIMEOUT) {
      Thread.sleep(1000);
      timer++;
    }
  }

  private static class TestResource extends DefaultCallbackResource {

    public Request request;

    @Override
    public Response post(Request request) {
      // store request to be used in test case
      this.request = request;
      return super.post(request);
    }
  }

  @Rule
  public Destination destination = new Destination("http://localhost:10043/test");

  @Test
  @HttpTest( method = Method.POST, 
             headers = { @Header(name = "test", value = "value") }, 
             path = "/", 
             authentications = { @Authentication(type = AuthenticationType.BASIC, user = "test", password = "pass") }, 
             type = MediaType.TEXT_PLAIN, 
             content = "test"
  )
  public void fakeTestMethod() {
    assertEquals("test", resource.request.getBody());
    Map<String, List<String>> headers = resource.request.getHeaders();
    assertTrue(headers.containsKey("Authorization"));
    assertEquals("value", headers.get("test").get(0));
    assertEquals(MediaType.TEXT_PLAIN, resource.request.getType());
  }

  @Test
  public void testPathWithSegments() {
    FrameworkMethod method = mock( FrameworkMethod.class );
    HttpTest annotation = createAnnotation( "/people/{id}/{name}" );
    when( method.getAnnotation( HttpTest.class ) ).thenReturn( annotation );
    RequestConfiguration config = new RequestConfiguration( "http://www.fake.com",
                                                            method,
                                                            new Object() );
    RequestContext context = new RequestContext();

    context.addPathSegment( "id", "12345" );
    context.addPathSegment( "name", "name" );
    InternalRequest request = config.createRequest( context );
    
    assertEquals( "http://www.fake.com/people/12345/name", request.getUrl() );
  }
  
  @Test( expected = IllegalStateException.class )
  public void testPathWithNonExistingSegments() {
    FrameworkMethod method = mock( FrameworkMethod.class );
    HttpTest annotation = createAnnotation( "/people/{invalid}/name" );
    when( method.getAnnotation( HttpTest.class ) ).thenReturn( annotation );
    RequestConfiguration config = new RequestConfiguration( "http://www.fake.com",
                                                            method,
                                                            new Object() );
    RequestContext context = new RequestContext();
    context.addPathSegment( "id", "12345" );
    config.createRequest( context );
  }

  private HttpTest createAnnotation(final String path) {
    HttpTest annotation = new HttpTest() {

      @Override
      public Class<? extends Annotation> annotationType() {
        return null;
      }

      @Override
      public MediaType type() {
        return MediaType.APPLICATION_JSON;
      }

      @Override
      public String path() {
        return path;
      }

      @Override
      public Method method() {
        return Method.GET;
      }

      @Override
      public Header[] headers() {
        return null;
      }

      @Override
      public String file() {
        return "";
      }

      @Override
      public String content() {
        return "content";
      }

      @Override
      public Authentication[] authentications() {
        return null;
      }

      @Override
      public int order() {
        return 0;
      }
    };
    return annotation;
  }

}
