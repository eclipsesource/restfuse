package com.eclipsesource.restfuse;

import java.util.Comparator;

import org.junit.runners.model.FrameworkMethod;

import com.eclipsesource.restfuse.annotation.HttpTest;


public class HttpOrderComparator implements Comparator<FrameworkMethod> {

  @Override
  public int compare( FrameworkMethod method1, FrameworkMethod method2 ) {
    HttpTest annotation1 = method1.getAnnotation( HttpTest.class );
    HttpTest annotation2 = method2.getAnnotation( HttpTest.class );
    if( annotation1 != null && annotation2 != null ) {
      return Integer.valueOf( annotation1.order() )
        .compareTo( Integer.valueOf( annotation2.order() ) );
    }
    return 0;
  }
}
