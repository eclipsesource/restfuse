package com.eclipsesource.restfuse;

import java.util.HashMap;

/**
 * Request context hold additional data to be added to the request before execution
 * like headers (cookies), authorization, ...  
 */
public class RequestContext {
  
  /**
   * Name value collection of headers
   */
  public HashMap<String, String> headers;

  // TODO: Add additional properties to be used at test runtime
  
  public RequestContext()
  {
    headers = new HashMap<String, String>();
  }  
}
