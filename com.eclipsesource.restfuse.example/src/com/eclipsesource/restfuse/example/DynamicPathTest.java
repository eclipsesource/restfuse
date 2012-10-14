package com.eclipsesource.restfuse.example;

import static com.eclipsesource.restfuse.Assert.assertOk;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.RequestContext;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

@RunWith(HttpJUnitRunner.class)
public class DynamicPathTest {

  @Rule
  public Destination restfuse = getDestination();
  
  @Context
  private Response response;

  @HttpTest(method = Method.GET, path = "/{file}.jar")
  public void checkRestfuseDownloadJarStatus() {
    assertOk( response );
  }
  
  @HttpTest(method = Method.GET, path = "/{file}-javadoc.jar")
  public void checkRestfuseDownloadDocsStatus() {
    assertOk( response );
  }

  private Destination getDestination() {
    Destination destination = new Destination( this, 
                                               "http://search.maven.org/remotecontent?filepath="
    		                                   + "com/restfuse/com.eclipsesource.restfuse/{version}/" );
    RequestContext context = destination.getRequestContext();
    context.addPathSegment( "file", "com.eclipsesource.restfuse-1.1.1" ).addPathSegment( "version", "1.1.1" );
    return destination;
  }
}
