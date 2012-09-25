package com.eclipsesource.restfuse.example;

import static com.eclipsesource.restfuse.Assert.assertOk;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

@RunWith(HttpJUnitRunner.class)
public class DynamicHeaderTest {

  @Rule
  public Destination restfuse = getDestination();
  @Context
  private Response response;

  @HttpTest(method = Method.GET, path = "/")
  public void checkRestfuseOnlineStatus() {
    assertOk( response );
  }

  private Destination getDestination() {
    Destination destination = new Destination( "http://restfuse.com" );
    destination.getRequestContext().addHeader( "Cookie", "name:value" );
    return destination;
  }
}
