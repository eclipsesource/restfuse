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
package com.eclipsesource.restfuse;

import static org.junit.Assert.fail;


/**
 * <p>The <code>Assert</code> class provides a set of convenience methods that ease the 
 * testing of an http response.</p> 
 */
public final class Assert {
  
  // Informational 1xx
  
  public static void assertContinue( Response response ) {
    doCheckStatus( Status.CONTINUE, response );
  }
  
  public static void assertSwitchingProtocols( Response response ) {
    doCheckStatus( Status.SWITCHING_PROTOCOLS, response );
  }
  
  // Successful 2xx
  
  public static void assertOk( Response response ) {
    doCheckStatus( Status.OK, response );
  }
  
  public static void assertCreated( Response response ) {
    doCheckStatus( Status.CREATED, response );
  }
  
  public static void assertAccepted( Response response ) {
    doCheckStatus( Status.ACCEPTED, response );
  }
  
  public static void assertNonAuthoritativeInformation( Response response ) {
    doCheckStatus( Status.NON_AUTHORITATIVE_INFORMATION, response );
  }
  
  public static void assertNoContent( Response response ) {
    doCheckStatus( Status.NO_CONTENT, response );
  }
  
  public static void assertResetContent( Response response ) {
    doCheckStatus( Status.RESET_CONTENT, response );
  }
  
  public static void assertPartialContent( Response response ) {
    doCheckStatus( Status.PARTIAL_CONTENT, response );
  }
  
  // Redirection 3xx
  
  public static void assertMultipleChoices( Response response ) {
    doCheckStatus( Status.MULTIPLE_CHOICES, response );
  }
  
  public static void assertMovedPermanently( Response response ) {
    doCheckStatus( Status.MOVED_PERMANENTLY, response );
  }
  
  public static void assertFound( Response response ) {
    doCheckStatus( Status.FOUND, response );
  }
  
  public static void assertSeeOther( Response response ) {
    doCheckStatus( Status.SEE_OTHER, response );
  }
  
  public static void assertNotModified( Response response ) {
    doCheckStatus( Status.NOT_MODIFIED, response );
  }
  
  public static void assertUseProxy( Response response ) {
    doCheckStatus( Status.USE_PROXY, response );
  }
  
  public static void assertTemporaryRedirected( Response response ) {
    doCheckStatus( Status.TEMPORARY_REDIRECT, response );
  }
  
  // Client Error 4xx
  
  public static void assertBadRequest( Response response ) {
    doCheckStatus( Status.BAD_REQUEST, response );
  }
  
  public static void assertUnauthorized( Response response ) {
    doCheckStatus( Status.UNAUTHORIZED, response );
  }
  
  public static void assertPaymentRequired( Response response ) {
    doCheckStatus( Status.PAYMENT_REQUIRED, response );
  }
  
  public static void assertForbidden( Response response ) {
    doCheckStatus( Status.FORBIDDEN, response );
  }
  
  public static void assertNotFound( Response response ) {
    doCheckStatus( Status.NOT_FOUND, response );
  }
  
  public static void assertMethodNotAllowed( Response response ) {
    doCheckStatus( Status.METHOD_NOT_ALLOWED, response );
  }
  
  public static void assertNotAcceptable( Response response ) {
    doCheckStatus( Status.NOT_ACCEPTABLE, response );
  }
  
  public static void assertProxyAuthenticationRequired( Response response ) {
    doCheckStatus( Status.PROXY_AUTHENTICATION_REQUIRED, response );
  }
  
  public static void assertRequestTimeout( Response response ) {
    doCheckStatus( Status.REQUEST_TIMEOUT, response );
  }
  
  public static void assertConflict( Response response ) {
    doCheckStatus( Status.CONFLICT, response );
  }
  
  public static void assertGone( Response response ) {
    doCheckStatus( Status.GONE, response );
  }
  
  public static void assertLengthRequired( Response response ) {
    doCheckStatus( Status.LENGTH_REQUIRED, response );
  }
  
  public static void assertPreconditionFailed( Response response ) {
    doCheckStatus( Status.PRECONDITION_FAILED, response );
  }
  
  public static void assertRequestEntityTooLarge( Response response ) {
    doCheckStatus( Status.REQUEST_ENTITY_TOO_LARGE, response );
  }
  
  public static void assertRequestURITooLong( Response response ) {
    doCheckStatus( Status.REQUEST_URI_TOO_LONG, response );
  }
  
  public static void assertUnsupportedMediaType( Response response ) {
    doCheckStatus( Status.UNSUPPORTED_MEDIA_TYPE, response );
  }
  
  public static void assertRequestedRangeNotSatisfiable( Response response ) {
    doCheckStatus( Status.REQUEST_RANGE_NOT_SATISFIABLE, response );
  }
  
  public static void assertExpectationFailed( Response response ) {
    doCheckStatus( Status.EXPECTATION_FAILED, response );
  }
  
  // Server Error 5xx
  
  public static void assertInternalServerError( Response response ) {
    doCheckStatus( Status.INTERNAL_SERVER_ERROR, response );
  }
  
  public static void assertNotImplemented( Response response ) {
    doCheckStatus( Status.NOT_IMPLEMENTED, response );
  }
  
  public static void assertBadGateway( Response response ) {
    doCheckStatus( Status.BAD_GATEWAY, response );
  }
  
  public static void assertServiceUnavailable( Response response ) {
    doCheckStatus( Status.SERVICE_UNAVAILABLE, response );
  }
  
  public static void assertGatewayTimeout( Response response ) {
    doCheckStatus( Status.GATEWAY_TIMEOUT, response );
  }
  
  public static void assertHTTPVersionNotSupported( Response response ) {
    doCheckStatus( Status.HTTP_VERSION_NOT_SUPPORTED, response );
  }
  
  private static void doCheckStatus( Status expected, Response response ) {
    assertStatusEquals( getDetailedErrorMessage( expected, response ),
                        expected.getStatusCode(),
                        response.getStatus() );
  }

  private static void assertStatusEquals( String detailedErrorMessage, int expected, int actual ) {
    if( expected != actual ) {
      fail( detailedErrorMessage );
    }
  }

  private static String getDetailedErrorMessage( Status expected, Response response ) {
    StringBuilder builder = new StringBuilder();
    builder.append( "Sent request to " + response.getUrl() );
    builder.append( "\n" );
    builder.append( "Response code did mot match expectation:" );
    builder.append( "\n" );
    builder.append( "Expected " + expected.getStatusCode() + " (" + expected.toString() + ") " );
    builder.append( "but was " + response.getStatus() );
    builder.append( " (" + Status.forStatusCode( response.getStatus() ) + ")" );
    builder.append( "\n\n" );
    return builder.toString();
  }
  
  private Assert() {
    // prevent instantiation
  }
}
