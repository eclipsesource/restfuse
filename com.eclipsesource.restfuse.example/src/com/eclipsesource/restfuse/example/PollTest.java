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

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.PollState;
import com.eclipsesource.restfuse.annotations.Context;
import com.eclipsesource.restfuse.annotations.HttpTest;
import com.eclipsesource.restfuse.annotations.Poll;


@RunWith( HttpJUnitRunner.class )
public class PollTest {

  @Rule
  public Destination restfuse = new Destination( "http://restfuse.com" );
  
  @Context
  private PollState pollState;

  @HttpTest( method = Method.GET, path = "/" ) 
  @Poll( times = 5, interval = 2000 )
  public void checkRestfuseOnlineStatus() {
    System.out.println( "Attemt " + pollState.getTimes() );
    System.out.println( pollState.getTimes() + ". Responsecode = " + pollState.getResponse( pollState.getTimes() ).getStatus() );
  }  
}
