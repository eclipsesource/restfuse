package com.eclipsesource.restfuse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.MediaType;


@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface HttpTest {

  Method method();

  String path();

  MediaType type() default MediaType.WILDCARD;

  String file() default EMPTY;

  String content() default EMPTY;

  Header[] headers() default {};

  Authentication[] authentications() default {};
  
  static final String EMPTY = "";

}
