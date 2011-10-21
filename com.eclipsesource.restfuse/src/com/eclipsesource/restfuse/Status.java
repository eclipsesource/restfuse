package com.eclipsesource.restfuse;
/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.php
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Original File: javax.ws.rs.core.Response.java
 * 
 * NOTE: This file contains a modified copy of the Response.Status Type.
 *
 * Created on April 18, 2007, 9:00 AM
 *
 */

public enum Status {

  OK( 200, "OK" ),
  CREATED( 201, "Created" ),
  ACCEPTED( 202, "Accepted" ),
  NO_CONTENT( 204, "No Content" ),
  MOVED_PERMANENTLY( 301, "Moved Permanently" ),
  SEE_OTHER( 303, "See Other" ),
  NOT_MODIFIED( 304, "Not Modified" ),
  TEMPORARY_REDIRECT( 307, "Temporary Redirect" ),
  BAD_REQUEST( 400, "Bad Request" ),
  UNAUTHORIZED( 401, "Unauthorized" ),
  FORBIDDEN( 403, "Forbidden" ),
  NOT_FOUND( 404, "Not Found" ),
  NOT_ACCEPTABLE( 406, "Not Acceptable" ),
  CONFLICT( 409, "Conflict" ),
  GONE( 410, "Gone" ),
  PRECONDITION_FAILED( 412, "Precondition Failed" ),
  UNSUPPORTED_MEDIA_TYPE( 415, "Unsupported Media Type" ),
  INTERNAL_SERVER_ERROR( 500, "Internal Server Error" ),
  SERVICE_UNAVAILABLE( 503, "Service Unavailable" );
        
  private final int code;
  private final String reason;

  Status( int statusCode, String reasonPhrase ) {
    this.code = statusCode;
    this.reason = reasonPhrase;
  }

  public int getStatusCode() {
    return code;
  }

  public String getReason() {
    return toString();
  }

  public static Status fromStatusCode( int statusCode ) {
    for( Status s : Status.values() ) {
      if( s.code == statusCode ) {
        return s;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return reason;
  }
}