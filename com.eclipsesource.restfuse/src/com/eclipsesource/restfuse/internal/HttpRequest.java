package com.eclipsesource.restfuse.internal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import com.eclipsesource.restfuse.AuthenticationType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.HTTPDigestAuthFilter;


public class HttpRequest {
  
  private final Map<String, List<String>> headers;
  private final List<AuthenticationInfo> authentications;
  private final String url;
  private InputStream content;
  private String mediaType;

  public HttpRequest( String url ) {
    this.url = url;
    headers = new HashMap<String, List<String>>();
    authentications = new ArrayList<AuthenticationInfo>();
  }

  public void setContentType( String mediaType ) {
    this.mediaType = mediaType;
  }

  public void setContent( InputStream content ) {
    this.content = content;
  }

  public void addHeader( String name, String value ) {
    List<String> param = headers.get( name );
    if( param == null ) {
      List<String> params = new ArrayList<String>();
      params.add( value );
      headers.put( name, params );
    } else {
      param.add( value );
    }
  }
  
  public void addAuthenticationInfo( AuthenticationInfo authentication ) {
    authentications.add( authentication );
  }

  public ClientResponse get() {
    ClientResponse result;
    try {
      result = createRequest().get( ClientResponse.class );
    } catch( Exception e ) {
      throw new IllegalStateException( e );
    }
    return result;
  }

  public ClientResponse post() {
    ClientResponse result;
    try {
      result = createRequest().post( ClientResponse.class, content );
    } catch( Exception e ) {
      throw new IllegalStateException( e );
    }
    return result;
  }
  
  public ClientResponse delete() {
    ClientResponse result;
    try {
      result = createRequest().delete( ClientResponse.class, content );
    } catch( Exception e ) {
      throw new IllegalStateException( e );
    }
    return result;
  }
  
  public ClientResponse put() {
    ClientResponse result;
    try {
      result = createRequest().put( ClientResponse.class, content );
    } catch( Exception e ) {
      throw new IllegalStateException( e );
    }
    return result;
  }
  
  public ClientResponse head() {
    ClientResponse result;
    try {
      result = createRequest().head();
    } catch( Exception e ) {
      throw new IllegalStateException( e );
    }
    return result;
  }
  
  public ClientResponse options() {
    ClientResponse result;
    try {
      result = createRequest().options( ClientResponse.class );
    } catch( Exception e ) {
      throw new IllegalStateException( e );
    }
    return result;
  }

  private Builder createRequest() {
    Client client = Client.create();
    addAuthentication( client );
    WebResource resource = client.resource( url );
    String type = mediaType != null ? mediaType : MediaType.WILDCARD;
    Builder builder = resource.type( type );
    builder = addHeaders( builder );
    return builder;
  }

  private void addAuthentication( Client client ) {
    for( AuthenticationInfo authentication : authentications ) {
      ClientFilter filter = null;
      if( authentication.getType().equals( AuthenticationType.BASIC ) ) {
        filter = new HTTPBasicAuthFilter( authentication.getUser(), authentication.getPassword() );
      } else if( authentication.getType().equals( AuthenticationType.DIGEST ) ) {
        filter = new HTTPDigestAuthFilter( authentication.getUser(), authentication.getPassword() );
      }
      client.addFilter( filter );
    }
  }

  private Builder addHeaders( Builder builder ) {
    Builder result = builder;
    Set<String> keySet = headers.keySet();
    for( String key : keySet ) {
      List<String> values = headers.get( key );
      for( String value : values ) {
        result = result.header( key, value );
      }
    }
    return result;
  }
}
