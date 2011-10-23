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


/**
 * <p>The <code>Status</code> enumeration contains values that can be used within a request/response 
 * to represent it's status code.</p>
 * 
 * @see Request
 * @see Response
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

  @Override
  public String toString() {
    return reason;
  }
}