package com.eclipsesource.restfuse;

import static com.eclipsesource.restfuse.Assert.assertAccepted;
import static com.eclipsesource.restfuse.Assert.assertBadRequest;
import static com.eclipsesource.restfuse.Assert.assertConflict;
import static com.eclipsesource.restfuse.Assert.assertCreated;
import static com.eclipsesource.restfuse.Assert.assertForbidden;
import static com.eclipsesource.restfuse.Assert.assertGone;
import static com.eclipsesource.restfuse.Assert.assertInternalServerError;
import static com.eclipsesource.restfuse.Assert.assertMovedPermanently;
import static com.eclipsesource.restfuse.Assert.assertNoContent;
import static com.eclipsesource.restfuse.Assert.assertNotAcceptable;
import static com.eclipsesource.restfuse.Assert.assertNotFound;
import static com.eclipsesource.restfuse.Assert.assertNotModified;
import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.Assert.assertPreconditionFailed;
import static com.eclipsesource.restfuse.Assert.assertSeeOther;
import static com.eclipsesource.restfuse.Assert.assertServiceUnavailable;
import static com.eclipsesource.restfuse.Assert.assertTemporaryRedirected;
import static com.eclipsesource.restfuse.Assert.assertUnauthorized;
import static com.eclipsesource.restfuse.Assert.assertUnsupportedMediaType;
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
  
}
