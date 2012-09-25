/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: drejc (https://github.com/drejc)
 *               Holger Staudacher - ongoing development
 *               Dennis Crissman - added dynamic path segments
 * 
 ******************************************************************************/
package com.eclipsesource.restfuse;

import java.util.HashMap;
import java.util.Map;

import com.eclipsesource.restfuse.annotation.Header;

/**
 * <p>
 * RequestContext holds additional data to be added to the request before execution like headers
 * (cookies) or dynamic path segments. The context is used to configure requests dynamically. A 
 * {@link RequestContext} can be created used {@link Destination#getRequestContext()}. 
 * </p>
 * 
 * @since 1.1
 */
public class RequestContext {
  
  /**
   * <p>
   * Name value collection of headers.
   * <p>
   * 
   * @since 1.1
   * @deprecated use {@link RequestContext#addHeader(String, String)}. Will be removed with 1.3!
   */
  public Map<String, String> headers = new HashMap<String, String>();
  
  private Map<String, String> dynamicPathSegments = new HashMap<String, String>();
  
  /**
   * <p>
   * Adds a header attribute to a request. 
   * <p>
   * @see Header
   * 
   * @since 1.2
   */
  public RequestContext addHeader( String name, String value ) {
    headers.put( name, value );
    return this;
  }

  /**
   * <p>
   * Dynamic path segments.Example with Given: http://localhost/{version}/{id}/<br>
   * 
   * <pre>
   *   Destination destination = new Destination( "http://localhost/{version}/" );
   *   RequestContext context = destination.getRequestContext();
   *   context.addPathSegment( "id", "12345" ).addPathSegment( "version", "1.1" );
   * </pre>
   * 
   * Produces: http://localhost/1.1/12345/
   * </p>
   * 
   * @since 1.2
   */
  public RequestContext addPathSegment( String segment, String replacement ) {
    dynamicPathSegments.put( segment, replacement );
    return this;
  }
  
  public Map<String, String> getHeaders() {
    return new HashMap<String, String>( headers );
  }
  
  public Map<String, String> getPathSegments() {
    return new HashMap<String, String>( dynamicPathSegments );
  }
  
}
