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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.eclipsesource.restfuse.annotations.HttpTest;


/**
 * <p>The <code>HttpJUnitRunner</code> can be used in your TestCase to avoid the double annotation
 * of test methods with the <code>Test</code> and </code>{@link HttpTest}</code> annotation. The
 * runner detects all <code>{@link HttpTest}</code> annotated methods and executes them as normal
 * JUnit test methods.</p>
 */
public class HttpJUnitRunner extends BlockJUnit4ClassRunner {

  public HttpJUnitRunner( Class<?> klass ) throws InitializationError {
    super( klass );
  }
  
  @Override
  protected List<FrameworkMethod> computeTestMethods() {
    ArrayList<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
    result.addAll( getTestClass().getAnnotatedMethods(HttpTest.class) );
    List<FrameworkMethod> testAnnotatedMethods = getTestClass().getAnnotatedMethods(Test.class);
    for( FrameworkMethod method : testAnnotatedMethods ) {
      if( !result.contains( method ) ) {
        result.add( method );
      }
    }
    return result;
  }
  
}
