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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.eclipsesource.restfuse.Status.Family;


public class Status_Test {
  
  @Test
  public void testAssignsFamily() {
    assertEquals( Family.INFORMATIONAL, Status.CONTINUE.getFamily() );
    assertEquals( Family.CLIENT_ERROR, Status.NOT_FOUND.getFamily() );
    assertEquals( Family.SERVER_ERROR, Status.INTERNAL_SERVER_ERROR.getFamily() );
    assertEquals( Family.SUCCESSFUL, Status.NO_CONTENT.getFamily() );
    assertEquals( Family.REDIRECTION, Status.MOVED_PERMANENTLY.getFamily() );
  }
}
