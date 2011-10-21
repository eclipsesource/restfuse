package com.eclipsesource.restfuse.annotations;

import com.eclipsesource.restfuse.AbstractCallback;


public @interface Callback {

  int port();

  String path();

  Class<? extends AbstractCallback> resource();
}
