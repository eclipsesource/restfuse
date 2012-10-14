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

import java.lang.reflect.Field;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.PollState;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.Poll;
import com.eclipsesource.restfuse.internal.HttpTestStatement;


public class PollStatement extends Statement {

  private final Statement statement;
  private final HttpTestStatement base;
  private int interval;
  private int times;
  private final Object target;
  private PollStateImpl pollState;

  public PollStatement( Statement statement, 
                        HttpTestStatement base, 
                        Description description, 
                        Object target ) 
  {
    this.statement = statement;
    this.base = base;
    this.target = target;
    Poll pollAnnotation = description.getAnnotation( Poll.class );
    interval = pollAnnotation.interval();
    times = pollAnnotation.times();
    pollState = new PollStateImpl();
  }

  @Override
  public void evaluate() throws Throwable {
    for( int i = 0; i < times && !pollState.wasAborted(); i++ ) {
      doSingleEvaluate();
    }
  }

  private void doSingleEvaluate() throws Throwable {
    Response response = base.sendRequest();
    base.tryInjectResponse( response );
    tryToInjectPollState( response );
    statement.evaluate();
    sleep();
  }

  private void tryToInjectPollState( Response response ) {
    pollState.addResponse( response );
    Field[] fields = target.getClass().getDeclaredFields();
    for( Field field : fields ) {
      Context contextAnnotation = field.getAnnotation( Context.class );
      if( contextAnnotation != null && field.getType() == PollState.class ) {
        injectPollState( field );
      }
    }
  }

  private void injectPollState( Field field ) {
    field.setAccessible( true );
    try {
      field.set( target, pollState );
    } catch( Exception exception ) {
      throw new IllegalStateException( "Could not inject pollstate.", exception );
    }
  }

  private void sleep() {
    try {
      Thread.sleep( interval );
    } catch( InterruptedException shouldNotHappen ) {
      throw new IllegalStateException( "Could not sleep until the next poll", shouldNotHappen );
    }
  }
}