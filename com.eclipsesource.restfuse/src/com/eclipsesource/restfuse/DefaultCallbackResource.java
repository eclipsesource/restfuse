package com.eclipsesource.restfuse;


public class DefaultCallbackResource extends CallbackResource {
  
  private static Response defaultResponse 
    = CallbackResource.createResponse( Status.NO_CONTENT, MediaType.WILDCARD, null, null );

  @Override
  public Response get( ServerRequest request ) {
    return defaultResponse;
  }

  @Override
  public Response post( ServerRequest request ) {
    return defaultResponse;
  }

  @Override
  public Response put( ServerRequest request ) {
    return defaultResponse;
  }

  @Override
  public Response delete( ServerRequest request ) {
    return defaultResponse;
  }

  @Override
  public Response head( ServerRequest request ) {
    return defaultResponse;
  }

  @Override
  public Response options( ServerRequest request ) {
    return defaultResponse;
  }
}
