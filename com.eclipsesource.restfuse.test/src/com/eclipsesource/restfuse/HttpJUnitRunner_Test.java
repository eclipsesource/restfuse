package com.eclipsesource.restfuse;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.eclipsesource.restfuse.annotations.HttpTest;


public class HttpJUnitRunner_Test {
  
  @Test
  public void testFindsTestMethods() throws InitializationError {
    HttpJUnitRunner runner = new HttpJUnitRunner( getClass() );
    
    List<FrameworkMethod> methods = runner.computeTestMethods();
    
    assertEquals( 3, methods.size() );
  }
  
  @HttpTest( method = Method.GET, path = "/" )
  public void fakeTestMethod() {
    // this method only exists to test that the runner finds HttpTest annotated methods
  }
  
  @Test
  @HttpTest( method = Method.GET, path = "/" )
  public void fakeTestMethodWithTwoTestAnnotations() {
    // this method only exists to test that the runner does not add this method twice 
  }
}
