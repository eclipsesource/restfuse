package com.eclipsesource.restfuse.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eclipsesource.restfuse.CallbackResource;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.Request;


public class CallbackSerlvet extends HttpServlet {
  
  private boolean wasCalled;
  private final CallbackResource resource;

  public CallbackSerlvet( CallbackResource resource ) {
    this.resource = resource;
  }
  
  @Override
  protected void doGet( HttpServletRequest req, HttpServletResponse resp )
    throws ServletException, IOException
  {
    createResponse( resp, resource.get( createRequest( req ) ) );
    wasCalled = true;
  }

  @Override
  protected void doPost( HttpServletRequest req, HttpServletResponse resp )
    throws ServletException, IOException
  {
    createResponse( resp, resource.post( createRequest( req ) ) );
    wasCalled = true;
  }
  
  @Override
  protected void doPut( HttpServletRequest req, HttpServletResponse resp )
    throws ServletException, IOException
  {
    createResponse( resp, resource.put( createRequest( req ) ) );
    wasCalled = true;
  }
  
  @Override
  protected void doDelete( HttpServletRequest req, HttpServletResponse resp )
    throws ServletException, IOException
  {
    createResponse( resp, resource.delete( createRequest( req ) ) );
    wasCalled = true;
  }
  
  @Override
  protected void doHead( HttpServletRequest req, HttpServletResponse resp )
    throws ServletException, IOException
  {
    createResponse( resp, resource.head( createRequest( req ) ) );
    wasCalled = true;
  }
  
  @Override
  protected void doOptions( HttpServletRequest req, HttpServletResponse resp )
    throws ServletException, IOException
  {
    createResponse( resp, resource.options( createRequest( req ) ) );
    wasCalled = true;
  }

  private Request createRequest( HttpServletRequest req ) {
    MediaType mediaType = MediaType.fromString( req.getContentType() );
    String body = getBody( req );
    RequestImpl result = new RequestImpl( body, mediaType );
    addHeaderToRequest( req, result );
    return result;
  }

  private void addHeaderToRequest( HttpServletRequest req, RequestImpl request ) {
    @SuppressWarnings( "rawtypes" )
    Enumeration headerNames = req.getHeaderNames();
    if( headerNames != null ) {
      while( headerNames.hasMoreElements() ) {
        String name = ( String )headerNames.nextElement();
        String value = req.getHeader( name );
        request.addHeader( name, value );
      }
    }
  }

  private String getBody( HttpServletRequest req ) {
    String result = null;
    try {
      BufferedReader reader = req.getReader();
      String buffer = "";
      StringBuilder stringBuilder = new StringBuilder();
      while( ( buffer = reader.readLine() ) != null ) {
        stringBuilder.append( buffer );
      }
      reader.close();
      result = stringBuilder.toString();
    } catch( IOException shouldNotHappen ) {
      throw new IllegalStateException( shouldNotHappen );
    }
    return result;
  }

  private void createResponse( HttpServletResponse resp, Response response ) throws IOException {
    resp.setStatus( response.getStatus() );
    addHeadersToResponse( resp, response );
    addContentTypeToResponse( resp, response );
    addBodyToResponse( resp, response );
  }

  private void addHeadersToResponse( HttpServletResponse resp, Response response ) {
    Map<String, List<String>> headers = response.getHeaders();
    if( headers != null ) {
      Set<String> keySet = headers.keySet();
      for( String key : keySet ) {
        List<String> values = headers.get( key );
        for( String value : values ) {
          resp.setHeader( key, value );
        }
      }
    }
  }

  private void addContentTypeToResponse( HttpServletResponse resp, Response response ) {
    MediaType type = response.getType();
    if( type != null ) {
      resp.setContentType( type.getMimeType() );
    }
  }

  private void addBodyToResponse( HttpServletResponse resp, Response response ) throws IOException {
    if( response.hasBody() ) {
      String body = response.getBody( String.class );
      resp.getWriter().write( body );
      resp.getWriter().close();
    }
  }

  public boolean wasCalled() {
    return wasCalled;
  }
}
