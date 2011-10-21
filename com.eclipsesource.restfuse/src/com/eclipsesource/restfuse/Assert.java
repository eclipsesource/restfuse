package com.eclipsesource.restfuse;

import static org.junit.Assert.assertEquals;


public final class Assert {
  
  public static void assertOk( Response response ) {
    doCheckStatus( Status.OK, response );
  }
  
  public static void assertNotFound( Response response ) {
    doCheckStatus( Status.NOT_FOUND, response );
  }
  
  public static void assertCreated( Response response ) {
    doCheckStatus( Status.CREATED, response );
  }
  
  public static void assertAccepted( Response response ) {
    doCheckStatus( Status.ACCEPTED, response );
  }
  
  public static void assertNoContent( Response response ) {
    doCheckStatus( Status.NO_CONTENT, response );
  }
  
  public static void assertMovedPermanently( Response response ) {
    doCheckStatus( Status.MOVED_PERMANENTLY, response );
  }
  
  public static void assertSeeOther( Response response ) {
    doCheckStatus( Status.SEE_OTHER, response );
  }
  
  public static void assertNotModified( Response response ) {
    doCheckStatus( Status.NOT_MODIFIED, response );
  }
  
  public static void assertTemporaryRedirected( Response response ) {
    doCheckStatus( Status.TEMPORARY_REDIRECT, response );
  }
  
  public static void assertBadRequest( Response response ) {
    doCheckStatus( Status.BAD_REQUEST, response );
  }
  
  public static void assertUnauthorized( Response response ) {
    doCheckStatus( Status.UNAUTHORIZED, response );
  }
  
  public static void assertForbidden( Response response ) {
    doCheckStatus( Status.FORBIDDEN, response );
  }
  
  public static void assertNotAcceptable( Response response ) {
    doCheckStatus( Status.NOT_ACCEPTABLE, response );
  }
  
  public static void assertConflict( Response response ) {
    doCheckStatus( Status.CONFLICT, response );
  }
  
  public static void assertGone( Response response ) {
    doCheckStatus( Status.GONE, response );
  }
  
  public static void assertPreconditionFailed( Response response ) {
    doCheckStatus( Status.PRECONDITION_FAILED, response );
  }
  
  public static void assertUnsupportedMediaType( Response response ) {
    doCheckStatus( Status.UNSUPPORTED_MEDIA_TYPE, response );
  }
  
  public static void assertInternalServerError( Response response ) {
    doCheckStatus( Status.INTERNAL_SERVER_ERROR, response );
  }
  
  public static void assertServiceUnavailable( Response response ) {
    doCheckStatus( Status.SERVICE_UNAVAILABLE, response );
  }
  
  private static void doCheckStatus( Status expected, Response response ) {
    assertEquals( expected.getStatusCode(), response.getStatus() );
  }
  
  private Assert() {
    // prevent instantiation
  }
}
