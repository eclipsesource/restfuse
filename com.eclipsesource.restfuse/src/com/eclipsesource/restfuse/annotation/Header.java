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
package com.eclipsesource.restfuse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>The <code>Header</code> annotation represents a single header attribute of an http request.
 * This headers can be set in a <code>{@link HttpTest}</code> annotation attached to a test method.
 * </p> 
 *
 * @see HttpTest
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.ANNOTATION_TYPE } )
public @interface Header {

  /**
   * <p>The <code>name</code> attribute represents the name of the header element which comes before 
   * the ":" within a request header.</p>
   */
  String name();

  /**
   * <p>The <code>value</code> attribute represents the value of the header element which comes 
   * after the ":" within a request header.</p>
   */
  String value();
}
