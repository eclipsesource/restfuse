package com.eclipsesource.restfuse.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.Status;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.internal.callback.CallbackSerlvet;
import com.eclipsesource.restfuse.internal.callback.CallbackStatement;

@RunWith(HttpJUnitRunner.class)
public class HttpTestStatementOrder_Test {

  private static int orderCheck = 0;
  
  private static final int TIMEOUT = 10;
  private static Server server;
  @Rule
  public Destination destination = new Destination( this, "http://localhost:10042/test" );
  @Context
  private Response response;

  @BeforeClass
  public static void setUp() throws Exception {
    server = new Server( 10042 );
    ServletContextHandler context = new ServletContextHandler( server,
                                                               "/",
                                                               ServletContextHandler.SESSIONS );
    CallbackStatement statement = mock( CallbackStatement.class );
    CallbackSerlvet servlet = new CallbackSerlvet( new DefaultCallbackResource(),
                                                   statement );
    context.addServlet( new ServletHolder( servlet ), "/" );
    server.start();
    int timer = 0;
    while( !server.isRunning() && timer < TIMEOUT ) {
      Thread.sleep( 1000 );
      timer++;
    }
  }

  @AfterClass
  public static void tearDown() throws Exception {
    server.stop();
    int timer = 0;
    while( !server.isStopped() && timer < TIMEOUT ) {
      Thread.sleep( 1000 );
      timer++;
    }
  }
  
  @HttpTest(method = Method.GET, path = "/")
  public void testNoOrder() {
    assertNotNull( response );
    assertEquals( Status.NO_CONTENT.getStatusCode(), response.getStatus() );
  }

  @HttpTest(method = Method.GET, path = "/", order = 2)
  public void testOrderSecond() {
    assertNotNull( response );
    assertEquals( Status.NO_CONTENT.getStatusCode(), response.getStatus() );
    
    orderCheck++;
    
    assertEquals( 2, orderCheck );
  }

  @HttpTest(method = Method.GET, path = "/", order = 3)
  public void testOrderThird() {
    assertNotNull( response );
    assertEquals( Status.NO_CONTENT.getStatusCode(), response.getStatus() );
    
    orderCheck++;
    
    assertEquals( 3, orderCheck );
  }

  @HttpTest(method = Method.GET, path = "/", order = 1)
  public void testOrderFirst() {
    assertNotNull( response );
    assertEquals( Status.NO_CONTENT.getStatusCode(), response.getStatus() );
    
    orderCheck++;
    
    assertEquals( 1, orderCheck );
  }

  @HttpTest(method = Method.GET, path = "/")
  public void testNoOrder_2() {
    assertNotNull( response );
    assertEquals( Status.NO_CONTENT.getStatusCode(), response.getStatus() );
  }
}
