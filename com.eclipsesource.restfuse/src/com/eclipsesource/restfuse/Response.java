package com.eclipsesource.restfuse;

import java.util.List;
import java.util.Map;


public interface Response {

  boolean hasBody();

  <T> T getBody( Class<T> type );

  MediaType getType();

  Map<String, List<String>> getHeaders();

  int getStatus();

}
