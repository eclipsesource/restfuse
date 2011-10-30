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

import com.eclipsesource.restfuse.annotation.Callback;


/**
 * <p>The <code>DefaultCallbackResource</code> is a default implementation for the 
 * <code>{@link CallbackResource}</code> which can be used within an http TestCase. All the methods
 * return a default http response with the 204 (no content) response code.</p>
 * 
 * @see Callback
 */
public class DefaultCallbackResource extends CallbackResource {
  
  private static Response defaultResponse 
    = CallbackResource.createResponse( Status.NO_CONTENT, MediaType.WILDCARD, null, null );

  @Override
  public Response get( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response post( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response put( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response delete( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response head( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response options( Request request ) {
    return defaultResponse;
  }
}
