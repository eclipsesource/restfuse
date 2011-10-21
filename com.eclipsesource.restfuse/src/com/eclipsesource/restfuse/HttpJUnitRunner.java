package com.eclipsesource.restfuse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.eclipsesource.restfuse.annotations.HttpTest;


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
