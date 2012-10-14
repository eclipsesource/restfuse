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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import com.eclipsesource.restfuse.AuthenticationType;
import com.eclipsesource.restfuse.Response;
import com.github.kevinsawicki.http.HttpRequest;


public class InternalRequest {
  
  private final Map<String, List<String>> headers;
  private final List<AuthenticationInfo> authentications;
  private final String url;
  private InputStream content;
  private String mediaType;

  public InternalRequest( String url ) {
    this.url = url;
    headers = new HashMap<String, List<String>>();
    authentications = new ArrayList<AuthenticationInfo>();
  }

  public void setContentType( String mediaType ) {
    this.mediaType = mediaType;
  }

  public void setContent( InputStream content ) {
    this.content = content;
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
  
  public void addAuthenticationInfo( AuthenticationInfo authentication ) {
    authentications.add( authentication );
  }

  public Response get() {
    HttpRequest request = HttpRequest.get( url );
    addContentType( request );
    addHeaders( request );
    addAuthentication( request );
    sendRequest( request );
    return new ResponseImpl( request );
  }

  public Response post() {
    HttpRequest request = HttpRequest.post( url );
    addContentType( request );
    addHeaders( request );
    addAuthentication( request );
    request.send( content );
    sendRequest( request );
    return new ResponseImpl( request );
  }
  
  public Response delete() {
    HttpRequest request = HttpRequest.delete( url );
    addContentType( request );
    addHeaders( request );
    addAuthentication( request );
    sendRequest( request );
    return new ResponseImpl( request );
  }
  
  public Response put() {
    HttpRequest request = HttpRequest.put( url );
    addContentType( request );
    addHeaders( request );
    addAuthentication( request );
    request.send( content );
    sendRequest( request );
    return new ResponseImpl( request );
  }
  
  public Response head() {
    HttpRequest request = HttpRequest.head( url );
    addContentType( request );
    addHeaders( request );
    addAuthentication( request );
    sendRequest( request );
    return new ResponseImpl( request );
  }
  
  public Response options() {
    HttpRequest request = HttpRequest.options( url );
    addContentType( request );
    addHeaders( request );
    addAuthentication( request );
    sendRequest( request );
    return new ResponseImpl( request );
  }


  private void addContentType( HttpRequest request ) {
    String type = mediaType != null ? mediaType : MediaType.WILDCARD;
    request.contentType( type );
  }

  private void addHeaders( HttpRequest request ) {
    Set<String> keySet = headers.keySet();
    for( String key : keySet ) {
      List<String> values = headers.get( key );
      StringBuilder builder = new StringBuilder();
      for( String value : values ) {
        builder.append( value + "," );
      }
      request.header( key, builder.substring( 0, builder.length() - 1 ) );
    }
    request.trustAllCerts();
    request.trustAllHosts();
  }

  private void addAuthentication( HttpRequest request ) {
    for( AuthenticationInfo authentication : authentications ) {
      if( authentication.getType().equals( AuthenticationType.BASIC ) ) {
        request.basic( authentication.getUser(), authentication.getPassword() );
      } else if( authentication.getType().equals( AuthenticationType.DIGEST ) ) {
        // TODO: implement digest auth
      }
    }
  }

  private void sendRequest( HttpRequest request ) {
    request.code();
  }

  String getUrl(){
    return url;
  }
}
