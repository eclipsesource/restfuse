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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.junit.runners.model.FrameworkMethod;

import com.eclipsesource.restfuse.AuthenticationType;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.annotations.Authentication;
import com.eclipsesource.restfuse.annotations.Header;
import com.eclipsesource.restfuse.annotations.HttpTest;


public class RequestConfiguration {
  
  private static final String PATH_SEPARATOR = "/";

  private final String baseUrl;
  private final FrameworkMethod method;
  private final Object target;

  public RequestConfiguration( String baseUrl, FrameworkMethod method, Object target ) {
    this.baseUrl = baseUrl;
    this.method = method;
    this.target = target;
  }

  public InternalRequest createRequest() {
    HttpTest call = method.getAnnotation( HttpTest.class );
    InternalRequest request = new InternalRequest( combineUrlAndPath( baseUrl, call.path() ) );
    addAuthentication( call, request );
    addContentType( call, request );
    addHeader( call, request );
    addBody( call, request );
    return request;
  }
  
  private void addAuthentication( HttpTest call, InternalRequest request ) {
    Authentication[] authentications = call.authentications();
    if( authentications != null ) {
      for( Authentication authentication : authentications ) {
        AuthenticationType type = authentication.type();
        String user = authentication.user();
        String password = authentication.password();
        request.addAuthenticationInfo( new AuthenticationInfo( type, user, password ) );
      }
    }
  }

  private void addContentType( HttpTest call, InternalRequest request ) {
    MediaType contentType = call.type();
    if( contentType != null ) {
      request.setContentType( contentType.getMimeType() );
    }
  }

  private void addHeader( HttpTest call, InternalRequest request ) {
    Header[] header = call.headers();
    if( header != null ) {
      for( Header parameter : header ) {
        request.addHeader( parameter.name(), parameter.value() );
      }
    }
  }

  private void addBody( HttpTest test, InternalRequest request ) {
    if( !test.file().equals( "" ) ) {
      request.setContent( getFileStream( test.file() ) );
    } else if( !test.content().equals( "" ) ) {
      request.setContent( getContentStream( test.content() ) );
    } 
  }

  private InputStream getFileStream( String file ) {
    URL resource = target.getClass().getResource( file );
    try {
      return resource.openStream();
    } catch( Exception ioe ) {
      throw new IllegalStateException( "Could not open file " 
                                       + file 
                                       + ". Maybe it's not on the classpath?" );
    }
  }

  private InputStream getContentStream( String content ) {
    try {
      return new ByteArrayInputStream( content.getBytes( "UTF-8" ) );
    } catch( UnsupportedEncodingException shouldNotHappen ) {
      throw new IllegalStateException( shouldNotHappen );
    }
  }

  private String combineUrlAndPath( String url, String pathValue ) {
    String result;
    if( url.endsWith( PATH_SEPARATOR ) && pathValue.startsWith( PATH_SEPARATOR ) ) {
      result = url + pathValue.substring( 1, pathValue.length() );
    } else if(    ( !url.endsWith( PATH_SEPARATOR ) && pathValue.startsWith( PATH_SEPARATOR ) ) 
               || ( url.endsWith( PATH_SEPARATOR ) && !pathValue.startsWith( PATH_SEPARATOR ) ) ) 
    {
      result = url + pathValue;
    } else if( !url.endsWith( PATH_SEPARATOR ) && !pathValue.startsWith( PATH_SEPARATOR ) ) {
      result = url + PATH_SEPARATOR + pathValue;
    } else {
      throw new IllegalStateException( "Invalid url format with base url " 
                                       + url
                                       + " and path " 
                                       + pathValue );
    }
    return result;
  }
}
