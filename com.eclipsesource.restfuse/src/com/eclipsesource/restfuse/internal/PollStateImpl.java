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

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.restfuse.PollState;
import com.eclipsesource.restfuse.Response;


public class PollStateImpl implements PollState {
  
  private final ArrayList<Response> responses;

  public PollStateImpl() {
    responses = new ArrayList<Response>();
  }

  @Override
  public int getTimes() {
    return responses.size();
  }

  @Override
  public void abort() {
    // TODO Auto-generated method stub
  }

  @Override
  public List<Response> getResponses() {
    return new ArrayList<Response>( responses );
  }

  @Override
  public Response getResponse( int attempt ) throws IllegalArgumentException {
    if( ( attempt ) > responses.size() ) {
      throw new IllegalArgumentException( "Response does not exist for attemt " + attempt );
    }
    return responses.get( attempt );
  }

  void addResponse( Response response ) {
    responses.add( response );
  }
}
