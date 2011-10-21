package com.eclipsesource.restfuse.annotations;

import com.eclipsesource.restfuse.AuthenticationType;


public @interface Authentication {

  AuthenticationType type();

  String user();

  String password();
  
}
