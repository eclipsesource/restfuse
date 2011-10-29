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


public interface PollState {
  
  int getTimes();
  
  void abort();
  
  List<Response> getResponses();
  
  Response getResponse( int attempt ) throws IllegalArgumentException;
  
}
