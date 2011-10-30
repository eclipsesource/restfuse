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
package com.eclipsesource.restfuse.internal;

import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.Response;


public class BasicStatement extends Statement {
  
  private final Statement statement;
  private final HttpTestStatement base;

  public BasicStatement( Statement statement, HttpTestStatement base ) {
    this.base = base;
    this.statement = statement;
  }

  @Override
  public void evaluate() throws Throwable {
    Response response = base.sendRequest();
    base.tryInjectResponse( response );
    statement.evaluate();
  }
}
