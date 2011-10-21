package com.eclipsesource.restfuse;


public class DefaultCallbackResource extends CallbackResource {
  
  private static Response defaultResponse 
    = CallbackResource.createResponse( Status.NO_CONTENT, MediaType.WILDCARD, null, null );

  @Override
  public Response get( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response post( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response put( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response delete( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response head( Request request ) {
    return defaultResponse;
  }

  @Override
  public Response options( Request request ) {
    return defaultResponse;
  }
}
