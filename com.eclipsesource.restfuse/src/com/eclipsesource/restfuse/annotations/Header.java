package com.eclipsesource.restfuse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.ANNOTATION_TYPE } )
public @interface Header {

  String name();

  String value();
}
