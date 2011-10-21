package com.eclipsesource.restfuse;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.annotations.HttpTest;
import com.eclipsesource.restfuse.internal.HttpTestStatement;


public class Destination implements MethodRule {

  private HttpTestStatement requestStatement;
  private final String baseUrl;

  public Destination( String baseUrl ) {
    checkBaseUrl( baseUrl );
    this.baseUrl = baseUrl;
  }

  private void checkBaseUrl( String baseUrl ) {
    if( baseUrl == null ) {
      throw new IllegalArgumentException( "baseUrl must not be null" );
    }
    try {
      new URL( baseUrl );
    } catch( MalformedURLException mue ) {
      throw new IllegalArgumentException( "baseUrl has to be an URL" );
    }
  }

  @Override
  public Statement apply( Statement base, FrameworkMethod method, Object target )
  {
    Statement result;
    if( hasAnnotation( method ) ) {
      requestStatement = new HttpTestStatement( base, method, target, baseUrl );
      result = requestStatement;
    } else {
      result = base;
    }
    return result;
  }

  private boolean hasAnnotation( FrameworkMethod method ) {
    return method.getAnnotation( HttpTest.class ) != null;
  }

  public Response getResponse() {
    if( requestStatement == null ) {
      throw new IllegalStateException( "Test method does not contain enough " 
                                       + "information to send the request" );
    }
    return requestStatement.getResponse();
  }
}
