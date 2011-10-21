package com.eclipsesource.restfuse;

import java.util.List;
import java.util.Map;


public interface ServerRequest {
  
  boolean hasBody();

  String getBody();

  MediaType getType();

  Map<String, List<String>> getHeaders();
  
}
