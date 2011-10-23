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
package com.eclipsesource.restfuse;

import com.eclipsesource.restfuse.annotations.Authentication;


/**
 * <p>An <code>AuthenticationType</code> can be used with the <code>{@link Authentication}</code>
 * annotation. Currently only BASIC and DIGEST authentication are supported</p>
 */
public enum AuthenticationType {
  BASIC, DIGEST
}
