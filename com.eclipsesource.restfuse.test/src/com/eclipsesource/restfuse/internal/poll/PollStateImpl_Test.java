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
package com.eclipsesource.restfuse.internal.poll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.eclipsesource.restfuse.Response;


public class PollStateImpl_Test {
  
  private PollStateImpl state;

  @Before
  public void setUp() {
    state = new PollStateImpl();
  }
  
  @Test
  public void testGetTimesFirstTimes() {
    assertEquals( 0, state.getTimes() );
  }
  
  @Test
  public void testGetTimesAfterResponse() {
    Response response = mock( Response.class );
    
    state.addResponse( response );
    
    assertEquals( 1, state.getTimes() );
  }
  
  @Test
  public void testGetTimesAfterTwoResponses() {
    state.addResponse( mock( Response.class ) );
    state.addResponse( mock( Response.class ) );
    state.addResponse( mock( Response.class ) );
    
    assertEquals( 3, state.getTimes() );
  }
  
  @Test
  public void testGetResponse() {
    state.addResponse( mock( Response.class ) );
    Response response = mock( Response.class );
    state.addResponse( response );
    Response response2 = mock( Response.class );
    state.addResponse( mock( Response.class ) );
    state.addResponse( response2 );
    
    assertSame( response, state.getResponse( 2 ) );
    assertSame( response2, state.getResponse( 4 ) );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void testGetNonExsitingResponse() {
    state.addResponse( mock( Response.class ) );
    Response response = mock( Response.class );
    state.addResponse( response );
    
    state.getResponse( 4 );
  }
  
  @Test
  public void testGetResponses() {
    Response response = mock( Response.class );
    state.addResponse( response );
    Response response2 = mock( Response.class );
    state.addResponse( response2 );

    List<Response> responses = state.getResponses();
    
    assertEquals( 2, responses.size() );
    assertEquals( response, responses.get( 0 ) );
    assertEquals( response2, responses.get( 1 ) );
  }
  
  @Test
  public void testAbort() {
    state.abort();
    
    assertTrue( state.wasAborted() );
  }
}
