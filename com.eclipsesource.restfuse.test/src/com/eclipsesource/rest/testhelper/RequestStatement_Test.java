package com.eclipsesource.rest.testhelper;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.internal.RequestStatement;


public class RequestStatement_Test {
  
  RequestStatement statement;
  private Statement base;
  private FrameworkMethod method;
  
  @Before
  public void setUp() {
    base = mock( Statement.class );
    method = mock( FrameworkMethod.class );
    statement = new RequestStatement( base, method, this );
  }
}
