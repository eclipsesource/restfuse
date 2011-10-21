package com.eclipsesource.restfuse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.annotations.HttpTest;
import com.eclipsesource.restfuse.internal.HttpTestStatement;


public class Destination_Test {
  
  private Destination destination;
  private Destination watchmanDestination;

  @Before
  public void setUp() {
    destination = new Destination( "http://localhost" );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void testBaseUrlCannotBeNull() {
    new Destination( null );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void testBaseUrlHasToBeAnUrl() {
    new Destination( "foo" );
  }
  
  @Test
  public void testApplyReturnsBaseWhenNoAnnotationsPresent() {
    Statement base = mock( Statement.class );
    FrameworkMethod method = mock( FrameworkMethod.class );
    
    Statement statement = destination.apply( base, method, this );
    
    assertEquals( base, statement );
  }
  
  @Rule
  public TestWatchman watchman = new TestWatchman() {

    public void starting( FrameworkMethod method ) {
      HttpTest annotation = method.getAnnotation( HttpTest.class );
      if( annotation != null ) {
        Statement base = mock( Statement.class );
        watchmanDestination = new Destination( "http://localhost" );
        Statement statement = watchmanDestination.apply( base, method, this );
        
        checkStatement( statement );
      }
    }
  };
  
  protected void checkStatement( Statement statement ) {
    assertTrue( statement instanceof HttpTestStatement );
  }

  @Test
  @HttpTest( method = Method.GET, path = "/" )
  public void testReturnsHttpTestStatement() {
    // nothing to do because the TestWatchman tests the Statement
  }
  
  @Test( expected = IllegalStateException.class )
  public void testFailsWithoutResponse() {
    destination.getResponse();
  }
  
  @Test
  @HttpTest( method = Method.GET, path = "/" )
  public void testReturnsResponseFromStatement() {
    Response actualResponse = watchmanDestination.getResponse();
    
    // We can test that the response is null because if no statement exist an ISE would be thrown
    assertNull( actualResponse );
  }
  
}
