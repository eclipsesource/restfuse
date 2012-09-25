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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Response;
import com.sun.jersey.api.client.ClientResponse;


public class ResponseImpl implements Response {

  private final ClientResponse clientResponse;
  private final String url;

  public ResponseImpl( String url, ClientResponse clientResponse ) {
    this.url = url;
    this.clientResponse = clientResponse;
  }
  
  @Override
  public boolean hasBody() {
    return clientResponse.hasEntity();
  }
  
  @Override
  public <T> T getBody( Class<T> type ) {
    return clientResponse.getEntity( type );
  }
  
  @Override
  public MediaType getType() {
    String type = clientResponse.getType().toString();
    return MediaType.fromString( type );
  }
  
  @Override
  public Map<String, List<String>> getHeaders() {
    Map<String, List<String>> result = new HashMap<String, List<String>>();
    MultivaluedMap<String, String> headers = clientResponse.getHeaders();
    Set<String> keySet = headers.keySet();
    for( String key : keySet ) {
      result.put( key, headers.get( key ) );
    }
    return result;
  }
  
  @Override
  public int getStatus() {
    return clientResponse.getStatus();
  }
  
  public String getUrl() {
    return url;
  }
}
