/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: Holger Staudacher - initial API and implementation
 * 
 ******************************************************************************/
package com.eclipsesource.restfuse.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.Description;

import com.eclipsesource.restfuse.AuthenticationType;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.RequestContext;
import com.eclipsesource.restfuse.annotation.Authentication;
import com.eclipsesource.restfuse.annotation.Header;
import com.eclipsesource.restfuse.annotation.HttpTest;

public class RequestConfiguration {

  private static final String PATH_SEPARATOR = "/";
  private final String baseUrl;
  private final Description description;
  private final Object target;

  public RequestConfiguration( String baseUrl, Description description, Object target ) {
    this.baseUrl = baseUrl;
    this.description = description;
    this.target = target;
  }

  public InternalRequest createRequest( RequestContext context ) {
    HttpTest call = description.getAnnotation( HttpTest.class );
    String rawPath = combineUrlAndPath( baseUrl, call.path() );
    InternalRequest request = new InternalRequest( substituePathSegments( rawPath, context ) );
    addAuthentication( call, request );
    addContentType( call, request );
    addHeader( call, request, context );
    addBody( call, request );
    return request;
  }

  private String substituePathSegments( String path, RequestContext context ) {
    String substitutedPath = path;
    Pattern pattern = Pattern.compile( ".*?\\{(.*?)\\}.*?" );
    Matcher matcher = pattern.matcher( path );
    while( matcher.find() ) {
      String segment = matcher.group( 1 );
      checkSubstitutionExists( context, segment );
      substitutedPath = substitutedPath.replace( "{" + segment + "}", 
                                                 context.getPathSegments().get( segment ) );
    }
    return substitutedPath;
  }

  private void checkSubstitutionExists( RequestContext context, String segment ) {
    if( !context.getPathSegments().containsKey( segment ) ) {
      throw new IllegalStateException( "Misconfigured Destination. Could not replace {" + segment + "}." );
    }
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

  private void addHeader( HttpTest call, InternalRequest request, RequestContext context ) {
    addHeadersFromContext( request, context );
    addHeadersFromAnnotation( call, request );
  }

  private void addHeadersFromContext( InternalRequest request, RequestContext context ) {
    if( context != null && !context.getHeaders().isEmpty() ) {
      Map<String, String> headers = context.getHeaders();
      for( String name : headers.keySet() )
        request.addHeader( name, headers.get( name ) );
    }
  }

  private void addHeadersFromAnnotation( HttpTest call, InternalRequest request ) {
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
    } else if( ( !url.endsWith( PATH_SEPARATOR ) && pathValue.startsWith( PATH_SEPARATOR ) )
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
