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
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.internal.HttpTestStatement;


public class Destination_Test {
  
  private Destination destination;
  private Destination watchmanDestination;

  @Before
  public void setUp() {
    destination = new Destination( this, "http://localhost" );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void testBaseUrlCannotBeNull() {
    new Destination( this, null );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void testBaseUrlHasToBeAnUrl() {
    new Destination( this, "foo" );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void testTestObjectIsNull() throws Exception {
    new Destination( null, "http://localhost" );
  }
  
  @Test
  public void testApplyReturnsBaseWhenNoAnnotationsPresent() {
    Statement base = mock( Statement.class );
    Description description = mock( Description.class );
    
    Statement statement = destination.apply( base, description );
    
    assertEquals( base, statement );
  }
  
  @Rule
  public TestWatcher watcher = new TestWatcher() {

    @Override
    public void starting( Description description ) {
      HttpTest annotation = description.getAnnotation( HttpTest.class );
      if( annotation != null ) {
        Statement base = mock( Statement.class );
        watchmanDestination = new Destination( new Object(), "http://localhost" );
        Statement statement = watchmanDestination.apply( base, description );
        
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
    // nothing to do because the TestWatcher tests the Statement
  }
  
}
