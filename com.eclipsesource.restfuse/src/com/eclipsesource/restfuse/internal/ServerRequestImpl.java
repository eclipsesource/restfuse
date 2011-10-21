package com.eclipsesource.restfuse.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.ServerRequest;


public class ServerRequestImpl implements ServerRequest {
  
  
  private Map<String, List<String>> headers;
  private final MediaType contentType;
  private final String body;

  public ServerRequestImpl( String body, MediaType contentType ) {
    this.body = body;
    this.contentType = contentType;
    headers = new HashMap<String, List<String>>();
  }

  @Override
  public boolean hasBody() {
    return body != null;
  }

  @Override
  public String getBody() {
    return body;
  }

  @Override
  public MediaType getType() {
    return contentType;
  }

  @Override
  public Map<String, List<String>> getHeaders() {
    return headers;
  }
  
  public void addHeader( String name, String value ) {
    List<String> param = headers.get( name );
    if( param == null ) {
      List<String> params = new ArrayList<String>();
      params.add( value );
      headers.put( name, params );
    } else {
      param.add( value );
    }
  }

}
