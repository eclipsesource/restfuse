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

import java.util.List;
import java.util.Map;


/**
 * <p>A <code>Response</code> acts as a wrapper for an http response.</p>
 */
public interface Response {

  boolean hasBody();

  /**
   * @deprecated use getBody() instead. May removed within the next iterations
   */
  <T> T getBody( Class<T> type );

  String getBody();

  MediaType getType();

  Map<String, List<String>> getHeaders();

  int getStatus();

  String getUrl();

}
