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

import java.util.List;

import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.Poll;


/**
 * <p>A <code>PollState</code> acts as a consistent state during a poll series. The 
 * <code>PollState</code> object will be injected into a test object when it has a field of the 
 * type <code>PollState</code> which is annotated with the <code>{@link Context}</code> 
 * annotation. After the injection it can be used to test the responses of the poll series.</p>
 * 
 * @see Poll
 * @see Context
 */
public interface PollState {
  
  /**
   * <p>Returns the number of the last request attempt. It can be used to get the current response 
   * when it will be passed to the <code>{@link PollState#getResponse(int)}</code> method.</p>
   */
  int getTimes();
  
  /**
   * <p>Aborts a poll series. After this method was called JUnit will continue with the next test
   * method.</p>
   */
  void abort();
  
  /**
   * <p>Returns all responses that were received during the poll series.</p>
   */
  List<Response> getResponses();
  
  /**
   * <p>Returns a single response for a specific request attempt with a poll series.</p>
   */
  Response getResponse( int attempt ) throws IllegalArgumentException;
  
}
