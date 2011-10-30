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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eclipsesource.restfuse.annotation.Callback;


/**
 * <p>A <code>CallbackResource</code> is used by the <code>{@link Callback}</code> annotation. This
 * class contains methods that will be called when there is an incoming request form a callback.
 * Subclasses need to override such methods to handle the request.</p>
 * 
 * <p>Instead of sub classing the <code>Callback</code> directly you may want to sub class the 
 * <code>{@link DefaultCallbackResource}</code> which provides a default implementation to return 
 * default responses.</p>
 *
 * @see Callback
 * @see DefaultCallbackResource
 */
public abstract class CallbackResource {

  private static class CallbackResponse implements Response {
    
    private final Status status;
    private final MediaType contentType;
    private final String body;
    private final Map<String, List<String>> headers;

    public CallbackResponse( Status status, 
                             MediaType contentType, 
                             String body, 
                             Map<String, List<String>> headers ) 
    {
      this.status = status;
      this.contentType = contentType;
      this.body = body;
      this.headers = headers;
    }

    @Override
    public boolean hasBody() {
      return body != null;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T> T getBody( Class<T> type ) {
      if( type != String.class ) {
        throw new IllegalStateException( "CallbackResponse can only have Strings as body" );
      }
      return ( T )body;
    }

    @Override
    public MediaType getType() {
      return contentType;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
      Map<String, List<String>> result = null;
      if( headers != null ) {
        result = new HashMap<String, List<String>>( headers );
      }
      return result;
    }

    @Override
    public int getStatus() {
      return status.getStatusCode();
    }
    
  }
  
  static Response createResponse( Status status, 
                                         MediaType contentType, 
                                         String body, 
                                         Map<String, List<String>> headers ) 
  {
    return new CallbackResponse( status, contentType, body, headers );
  }

  /**
   * <p>Will be called during an http get request.</p>
   */
  public abstract Response get( Request request );

  /**
   * <p>Will be called during an http post request.</p>
   */
  public abstract Response post( Request request );

  /**
   * <p>Will be called during an http put request.</p>
   */
  public abstract Response put( Request request );

  /**
   * <p>Will be called during an http delete request.</p>
   */
  public abstract Response delete( Request request );

  /**
   * <p>Will be called during an http head request.</p>
   */
  public abstract Response head( Request request );

  /**
   * <p>Will be called during an http options request.</p>
   */
  public abstract Response options( Request request );
  
}
