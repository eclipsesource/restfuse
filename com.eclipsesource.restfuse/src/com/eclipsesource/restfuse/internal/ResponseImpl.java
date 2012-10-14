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

import java.util.List;
import java.util.Map;

import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Response;
import com.github.kevinsawicki.http.HttpRequest;


public class ResponseImpl implements Response {
  
  private final HttpRequest request;

  public ResponseImpl( HttpRequest request ) {
    this.request = request;
    request.disconnect();
  }

  @Override
  public boolean hasBody() {
    return request.body() != null;
  }

  @Override
  @SuppressWarnings( "unchecked" )
  public <T> T getBody( Class<T> type ) {
    if( type != String.class ) {
      throw new IllegalArgumentException( "Only String is supported. Not the this method is deprecated, see getBody()." );
    }
    return ( T )request.body();
  }

  @Override
  public String getBody() {
    return request.body();
  }

  @Override
  public MediaType getType() {
    return MediaType.fromString( request.contentType() );
  }

  @Override
  public Map<String, List<String>> getHeaders() {
    return request.headers();
  }

  @Override
  public int getStatus() {
    return request.code();
  }

  @Override
  public String getUrl() {
    return request.getConnection().getURL().toString();
  }
}
