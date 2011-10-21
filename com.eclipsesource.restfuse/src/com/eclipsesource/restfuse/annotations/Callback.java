package com.eclipsesource.restfuse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eclipsesource.restfuse.CallbackResource;


@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface Callback {

  int port();

  String path();

  Class<? extends CallbackResource> resource();

  int timeout();
}
