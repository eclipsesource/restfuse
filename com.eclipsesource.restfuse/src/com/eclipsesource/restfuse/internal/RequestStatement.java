package com.eclipsesource.restfuse.internal;

import java.lang.reflect.Field;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotations.HttpContext;
import com.eclipsesource.restfuse.annotations.HttpTest;
import com.sun.jersey.api.client.ClientResponse;


public class RequestStatement extends Statement {

  private final Statement base;
  private final FrameworkMethod method;
  private final Object target;
  private final String baseUrl;
  private Response response;

  public RequestStatement( Statement base, FrameworkMethod method, Object target, String baseUrl ) {
    this.base = base;
    this.method = method;
    this.target = target;
    this.baseUrl = baseUrl;
  }

  public Response getResponse() {
    return response;
  }

  @Override
  public void evaluate() throws Throwable {
    try {
      sendRequest();
      tryInjectResponse();
    } finally {
      base.evaluate();
    }
  }

  private void sendRequest() {
    HttpRequest request = buildRequest();
    ClientResponse clientResponse = callService( request );
    response = new ResponseImpl( clientResponse );
  }

  private HttpRequest buildRequest() {
    RequestConfiguration requestConfiguration = new RequestConfiguration( baseUrl, method, target );
    return requestConfiguration.createRequest();
  }

  private ClientResponse callService( HttpRequest request ) {
    Method requestMethod = method.getAnnotation( HttpTest.class ).method();
    ClientResponse result = null;
    if( requestMethod.equals( Method.GET ) ) {
      result = request.get();
    } else if( requestMethod.equals( Method.POST ) ) {
      result = request.post();
    } else if( requestMethod.equals( Method.DELETE ) ) {
      result = request.delete();
    } else if( requestMethod.equals( Method.PUT ) ) {
      result = request.put();
    } else if( requestMethod.equals( Method.HEAD ) ) {
      result = request.head();
    } else if( requestMethod.equals( Method.OPTIONS ) ) {
      result = request.options();
    }
    return result;
  }

  private void tryInjectResponse() {
    Field[] fields = target.getClass().getDeclaredFields();
    for( Field field : fields ) {
      HttpContext responseAnnotation = field.getAnnotation( HttpContext.class );
      if( responseAnnotation != null && field.getType() == Response.class ) {
        injectResponse( field );
      }
    }
  }

  private void injectResponse( Field field ) {
    field.setAccessible( true );
    try {
      field.set( target, response );
    } catch( Exception exception ) {
      throw new IllegalStateException( "Could not inject response.", exception );
    }
  }

}
