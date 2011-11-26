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

import static com.eclipsesource.restfuse.Assert.assertAccepted;
import static com.eclipsesource.restfuse.Assert.assertBadGateway;
import static com.eclipsesource.restfuse.Assert.assertBadRequest;
import static com.eclipsesource.restfuse.Assert.assertConflict;
import static com.eclipsesource.restfuse.Assert.assertContinue;
import static com.eclipsesource.restfuse.Assert.assertCreated;
import static com.eclipsesource.restfuse.Assert.assertExpectationFailed;
import static com.eclipsesource.restfuse.Assert.assertForbidden;
import static com.eclipsesource.restfuse.Assert.assertFound;
import static com.eclipsesource.restfuse.Assert.assertGatewayTimeout;
import static com.eclipsesource.restfuse.Assert.assertGone;
import static com.eclipsesource.restfuse.Assert.assertHTTPVersionNotSupported;
import static com.eclipsesource.restfuse.Assert.assertInternalServerError;
import static com.eclipsesource.restfuse.Assert.assertLengthRequired;
import static com.eclipsesource.restfuse.Assert.assertMethodNotAllowed;
import static com.eclipsesource.restfuse.Assert.assertMovedPermanently;
import static com.eclipsesource.restfuse.Assert.assertMultipleChoices;
import static com.eclipsesource.restfuse.Assert.assertNoContent;
import static com.eclipsesource.restfuse.Assert.assertNonAuthoritativeInformation;
import static com.eclipsesource.restfuse.Assert.assertNotAcceptable;
import static com.eclipsesource.restfuse.Assert.assertNotFound;
import static com.eclipsesource.restfuse.Assert.assertNotImplemented;
import static com.eclipsesource.restfuse.Assert.assertNotModified;
import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.Assert.assertPartialContent;
import static com.eclipsesource.restfuse.Assert.assertPaymentRequired;
import static com.eclipsesource.restfuse.Assert.assertPreconditionFailed;
import static com.eclipsesource.restfuse.Assert.assertProxyAuthenticationRequired;
import static com.eclipsesource.restfuse.Assert.assertRequestEntityTooLarge;
import static com.eclipsesource.restfuse.Assert.assertRequestTimeout;
import static com.eclipsesource.restfuse.Assert.assertRequestURITooLong;
import static com.eclipsesource.restfuse.Assert.assertRequestedRangeNotSatisfiable;
import static com.eclipsesource.restfuse.Assert.assertResetContent;
import static com.eclipsesource.restfuse.Assert.assertSeeOther;
import static com.eclipsesource.restfuse.Assert.assertServiceUnavailable;
import static com.eclipsesource.restfuse.Assert.assertSwitchingProtocols;
import static com.eclipsesource.restfuse.Assert.assertTemporaryRedirected;
import static com.eclipsesource.restfuse.Assert.assertUnauthorized;
import static com.eclipsesource.restfuse.Assert.assertUnsupportedMediaType;
import static com.eclipsesource.restfuse.Assert.assertUseProxy;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.Status;


@RunWith( MockitoJUnitRunner.class )
public class Assert_Test {
  
  @Mock
  Response response;
  
  @Test
  public void testAssertAccpeted() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertAccepted( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertAccpetedFails() {
    when( response.getStatus() ).thenReturn( Status.NOT_FOUND.getStatusCode() );
    
    assertAccepted( response );
  }
  
  @Test
  public void testAssertNotFound() {
    when( response.getStatus() ).thenReturn( Status.NOT_FOUND.getStatusCode() );
    
    assertNotFound( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertNotFoundFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertNotFound( response );
  }
  
  @Test
  public void testAssertCreated() {
    when( response.getStatus() ).thenReturn( Status.CREATED.getStatusCode() );
    
    assertCreated( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertCreatedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertCreated( response );
  }
  
  @Test
  public void testAssertOk() {
    when( response.getStatus() ).thenReturn( Status.OK.getStatusCode() );
    
    assertOk( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertOkFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertOk( response );
  }
  
  @Test
  public void testAssertNoContent() {
    when( response.getStatus() ).thenReturn( Status.NO_CONTENT.getStatusCode() );
    
    assertNoContent( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertNoContentFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertNoContent( response );
  }
  
  @Test
  public void testAssertMovedPermanently() {
    when( response.getStatus() ).thenReturn( Status.MOVED_PERMANENTLY.getStatusCode() );
    
    assertMovedPermanently( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertMovedPermanentlyFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertMovedPermanently( response );
  }
  
  @Test
  public void testAssertSeeOther() {
    when( response.getStatus() ).thenReturn( Status.SEE_OTHER.getStatusCode() );
    
    assertSeeOther( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertSeeOtherFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertSeeOther( response );
  }
  
  @Test
  public void testAssertNotModified() {
    when( response.getStatus() ).thenReturn( Status.NOT_MODIFIED.getStatusCode() );
    
    assertNotModified( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertNotModifiedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertNotModified( response );
  }
  
  @Test
  public void testAssertTemporaryRedirected() {
    when( response.getStatus() ).thenReturn( Status.TEMPORARY_REDIRECT.getStatusCode() );
    
    assertTemporaryRedirected( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertTemporaryRedirectedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertTemporaryRedirected( response );
  }
  
  @Test
  public void testAssertBadRequest() {
    when( response.getStatus() ).thenReturn( Status.BAD_REQUEST.getStatusCode() );
    
    assertBadRequest( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertBadRequestFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertBadRequest( response );
  }
  
  @Test
  public void testAssertUnauthorized() {
    when( response.getStatus() ).thenReturn( Status.UNAUTHORIZED.getStatusCode() );
    
    assertUnauthorized( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertUnauthorizedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertUnauthorized( response );
  }
  
  @Test
  public void testAssertForbidden() {
    when( response.getStatus() ).thenReturn( Status.FORBIDDEN.getStatusCode() );
    
    assertForbidden( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertForbiddenFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertForbidden( response );
  }
  
  @Test
  public void testAssertNotAcceptable() {
    when( response.getStatus() ).thenReturn( Status.NOT_ACCEPTABLE.getStatusCode() );
    
    assertNotAcceptable( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertNotAcceptableFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertNotAcceptable( response );
  }
  
  @Test
  public void testAssertConflict() {
    when( response.getStatus() ).thenReturn( Status.CONFLICT.getStatusCode() );
    
    assertConflict( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertConflictFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertConflict( response );
  }
  
  @Test
  public void testAssertGone() {
    when( response.getStatus() ).thenReturn( Status.GONE.getStatusCode() );
    
    assertGone( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertGoneFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertGone( response );
  }
  
  @Test
  public void testAssertPreconditionFailed() {
    when( response.getStatus() ).thenReturn( Status.PRECONDITION_FAILED.getStatusCode() );
    
    assertPreconditionFailed( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertPreconditionFailedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertPreconditionFailed( response );
  }
  
  @Test
  public void testAssertUnsupportedMediatype() {
    when( response.getStatus() ).thenReturn( Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode() );
    
    assertUnsupportedMediaType( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertUnsupportedMediatypeFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertUnsupportedMediaType( response );
  }
  
  @Test
  public void testAssertInternalServerError() {
    when( response.getStatus() ).thenReturn( Status.INTERNAL_SERVER_ERROR.getStatusCode() );
    
    assertInternalServerError( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertInternalServerErrorFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertInternalServerError( response );
  }
  
  @Test
  public void testAssertServiceUnavailable() {
    when( response.getStatus() ).thenReturn( Status.SERVICE_UNAVAILABLE.getStatusCode() );
    
    assertServiceUnavailable( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertServiceUnavailableFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertServiceUnavailable( response );
  }
  
  @Test
  public void testAssertContinue() {
    when( response.getStatus() ).thenReturn( Status.CONTINUE.getStatusCode() );
    
    assertContinue( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertContinueFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertContinue( response );
  }
  
  @Test
  public void testAssertSwitchingProtocols() {
    when( response.getStatus() ).thenReturn( Status.SWITCHING_PROTOCOLS.getStatusCode() );
    
    assertSwitchingProtocols( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertSwitchingProtocolsFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertSwitchingProtocols( response );
  }
  
  @Test
  public void testAssertNonAuthoritativeInformation() {
    when( response.getStatus() ).thenReturn( Status.NON_AUTHORITATIVE_INFORMATION.getStatusCode() );
    
    assertNonAuthoritativeInformation( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertNonAuthoritativeInformationFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertNonAuthoritativeInformation( response );
  }
  
  @Test
  public void testAssertResetContent() {
    when( response.getStatus() ).thenReturn( Status.RESET_CONTENT.getStatusCode() );
    
    assertResetContent( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertResetContentFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertResetContent( response );
  }
  
  @Test
  public void testAssertPartialContent() {
    when( response.getStatus() ).thenReturn( Status.PARTIAL_CONTENT.getStatusCode() );
    
    assertPartialContent( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertPartialContentFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertPartialContent( response );
  }
  
  @Test
  public void testAssertMultipleChoices() {
    when( response.getStatus() ).thenReturn( Status.MULTIPLE_CHOICES.getStatusCode() );
    
    assertMultipleChoices( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertMultipleChoicesFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertMultipleChoices( response );
  }
  
  @Test
  public void testAssertFound() {
    when( response.getStatus() ).thenReturn( Status.FOUND.getStatusCode() );
    
    assertFound( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertFoundFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertFound( response );
  }
  
  @Test
  public void testAssertUseProxy() {
    when( response.getStatus() ).thenReturn( Status.USE_PROXY.getStatusCode() );
    
    assertUseProxy( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertUseProxyFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertUseProxy( response );
  }
  
  @Test
  public void testAssertPaymentRequired() {
    when( response.getStatus() ).thenReturn( Status.PAYMENT_REQUIRED.getStatusCode() );
    
    assertPaymentRequired( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertPaymentRequiredFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertPaymentRequired( response );
  }
  
  @Test
  public void testAssertMethodNotAllowed() {
    when( response.getStatus() ).thenReturn( Status.METHOD_NOT_ALLOWED.getStatusCode() );
    
    assertMethodNotAllowed( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertMethodNotAllowedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertMethodNotAllowed( response );
  }
  
  @Test
  public void testAssertProxyAuthenticationRequired() {
    when( response.getStatus() ).thenReturn( Status.PROXY_AUTHENTICATION_REQUIRED.getStatusCode() );
    
    assertProxyAuthenticationRequired( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertProxyAuthenticationRequiredFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertProxyAuthenticationRequired( response );
  }
  
  @Test
  public void testAssertRequestTimeout() {
    when( response.getStatus() ).thenReturn( Status.REQUEST_TIMEOUT.getStatusCode() );
    
    assertRequestTimeout( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertRequestTimeoutFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertRequestTimeout( response );
  }
  
  @Test
  public void testAssertLengthRequired() {
    when( response.getStatus() ).thenReturn( Status.LENGTH_REQUIRED.getStatusCode() );
    
    assertLengthRequired( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertLengthRequiredFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertLengthRequired( response );
  }
  
  @Test
  public void testAssertRequestEntityTooLarge() {
    when( response.getStatus() ).thenReturn( Status.REQUEST_ENTITY_TOO_LARGE.getStatusCode() );
    
    assertRequestEntityTooLarge( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertRequestEntityTooLargeFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertRequestEntityTooLarge( response );
  }
  
  @Test
  public void testAssertRequestURITooLong() {
    when( response.getStatus() ).thenReturn( Status.REQUEST_URI_TOO_LONG.getStatusCode() );
    
    assertRequestURITooLong( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertRequestURITooLongFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertRequestURITooLong( response );
  }
  
  @Test
  public void testAssertRequestedRangeNotSatisfiable() {
    when( response.getStatus() ).thenReturn( Status.REQUEST_RANGE_NOT_SATISFIABLE.getStatusCode() );
    
    assertRequestedRangeNotSatisfiable( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertRequestedRangeNotSatisfiableFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertRequestedRangeNotSatisfiable( response );
  }
  
  @Test
  public void testAssertExpectationFailed() {
    when( response.getStatus() ).thenReturn( Status.EXPECTATION_FAILED.getStatusCode() );
    
    assertExpectationFailed( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertExpectationFailedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertExpectationFailed( response );
  }
  
  @Test
  public void testAssertNotImplemented() {
    when( response.getStatus() ).thenReturn( Status.NOT_IMPLEMENTED.getStatusCode() );
    
    assertNotImplemented( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertNotImplementedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertNotImplemented( response );
  }
  
  @Test
  public void testAssertBadGateway() {
    when( response.getStatus() ).thenReturn( Status.BAD_GATEWAY.getStatusCode() );
    
    assertBadGateway( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertBadGatewayFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertBadGateway( response );
  }
  
  @Test
  public void testAssertGatewayTimeout() {
    when( response.getStatus() ).thenReturn( Status.GATEWAY_TIMEOUT.getStatusCode() );
    
    assertGatewayTimeout( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertGatewayTimeoutFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertGatewayTimeout( response );
  }
  
  @Test
  public void testAssertHTTPVersionNotSupported() {
    when( response.getStatus() ).thenReturn( Status.HTTP_VERSION_NOT_SUPPORTED.getStatusCode() );
    
    assertHTTPVersionNotSupported( response );
  }
  
  @Test( expected = AssertionError.class )
  public void testAssertHTTPVersionNotSupportedFails() {
    when( response.getStatus() ).thenReturn( Status.ACCEPTED.getStatusCode() );
    
    assertHTTPVersionNotSupported( response );
  }
  
}
