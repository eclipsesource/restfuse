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

import com.eclipsesource.restfuse.annotation.HttpTest;

/**
 * <p>The <code>Method</code> enumeration contains values that represents http methods. 
 * Such a method needs to be set when using the <code>{@link HttpTest}</code> annotation on your
 * test method.</p>
 */
public enum Method {
  POST, GET, DELETE, PUT, HEAD, OPTIONS
}
