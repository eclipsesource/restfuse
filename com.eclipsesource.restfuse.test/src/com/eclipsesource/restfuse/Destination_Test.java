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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.annotation.HttpTest;
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
  
}
