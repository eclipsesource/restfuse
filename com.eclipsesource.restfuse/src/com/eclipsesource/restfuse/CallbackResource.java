package com.eclipsesource.restfuse;

import java.util.List;
import java.util.Map;



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
      return headers;
    }

    @Override
    public int getStatus() {
      return status.getStatusCode();
    }
    
  }
  
  public static Response createResponse( Status status, 
                                                 MediaType contentType, 
                                                 String body, 
                                                 Map<String, List<String>> headers ) 
  {
    return new CallbackResponse( status, contentType, body, headers );
  }

  public abstract Response get( Request request );

  public abstract Response post( Request request );

  public abstract Response put( Request request );

  public abstract Response delete( Request request );

  public abstract Response head( Request request );

  public abstract Response options( Request request );
}
