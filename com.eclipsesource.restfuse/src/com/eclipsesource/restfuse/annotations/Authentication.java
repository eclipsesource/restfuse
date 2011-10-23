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
package com.eclipsesource.restfuse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eclipsesource.restfuse.AuthenticationType;


/**
 * <p>The <code>Authentication</code> can be used within a <code>{@link HttpTest}</code> annotation
 * to send authentication information within an outgoing http request. Currently only BASIC and
 * DIGEST authentication are supported.</p>
 * 
 * @see AuthenticationType
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.ANNOTATION_TYPE } )
public @interface Authentication {
  
  /**
   * <p>The <code>type</code> attribute specifies the type of the authentication method. Currently
   * only BASIC and DIGEST authentication are supported. This may change in future.</p>
   * 
   * @see AuthenticationType
   */
  AuthenticationType type();

  String user();

  String password();
  
}
