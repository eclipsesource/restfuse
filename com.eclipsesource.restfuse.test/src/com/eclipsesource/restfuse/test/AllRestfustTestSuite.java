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
package com.eclipsesource.restfuse.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.eclipsesource.restfuse.Assert_Test;
import com.eclipsesource.restfuse.CallbackResource_Test;
import com.eclipsesource.restfuse.DefaultCallbackResource_Test;
import com.eclipsesource.restfuse.Destination_Test;
import com.eclipsesource.restfuse.HttpJUnitRunner_Test;
import com.eclipsesource.restfuse.Poll_Test;
import com.eclipsesource.restfuse.internal.AuthenticationInfo_Test;
import com.eclipsesource.restfuse.internal.HttpTestStatement_Test;
import com.eclipsesource.restfuse.internal.InternalRequest_Test;
import com.eclipsesource.restfuse.internal.RequestConfiguration_Test;
import com.eclipsesource.restfuse.internal.RequestImpl_Test;
import com.eclipsesource.restfuse.internal.ResponseImpl_Test;
import com.eclipsesource.restfuse.internal.callback.CallbackServer_Test;
import com.eclipsesource.restfuse.internal.callback.CallbackServlet_Test;
import com.eclipsesource.restfuse.internal.poll.PollStateImpl_Test;


@RunWith( Suite.class ) 
@SuiteClasses( {
  Assert_Test.class,
  CallbackResource_Test.class,
  DefaultCallbackResource_Test.class,
  Destination_Test.class,
  HttpJUnitRunner_Test.class,
  AuthenticationInfo_Test.class,
  CallbackServer_Test.class,
  CallbackServlet_Test.class,
  HttpTestStatement_Test.class,
  InternalRequest_Test.class,
  RequestConfiguration_Test.class,
  RequestImpl_Test.class,
  ResponseImpl_Test.class,
  PollStateImpl_Test.class,
  Poll_Test.class
} )

public class AllRestfustTestSuite {
}
