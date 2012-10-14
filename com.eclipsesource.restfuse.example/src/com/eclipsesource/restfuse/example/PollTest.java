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
package com.eclipsesource.restfuse.example;

import static com.eclipsesource.restfuse.Assert.assertOk;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.PollState;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.Header;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.annotation.Poll;


@RunWith( HttpJUnitRunner.class )
public class PollTest {

  @Rule
  public Destination restfuse = new Destination( this, "http://restfuse.com" );
  
  @Context
  private PollState pollState;
  
  @Context
  private Response response;

  @HttpTest( method = Method.GET, path = "/" ) 
  @Poll( times = 3, interval = 2000 )
  public void checkRestfuseOnlineStatus() {
    System.out.println( "Attemt " + pollState.getTimes() );
    System.out.println( pollState.getTimes() + ". Responsecode = " + pollState.getResponse( pollState.getTimes() ).getStatus() );
  }  
  
  @HttpTest( method = Method.GET, 
             path = "/", 
             headers = { @Header( name = "Accepted-Language", value = "en-en" ) } ) 
  public void checkRestfuseOnlineStatusWithHeader() {
    assertOk( response );
  }  
}
