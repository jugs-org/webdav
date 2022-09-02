/*
 * Copyright 2008, 2009 Daniel MANZKE
 *
 * This file is part of webdav-jaxrs.
 *
 * webdav-jaxrs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * webdav-jaxrs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with webdav-jaxrs.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jugs.webdav.fileserver.resources;

import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.jersey.api.core.HttpResponseContext;
import com.sun.jersey.server.impl.application.WebApplicationContext;
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.LockDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.jugs.webdav.jaxrs.Headers.DAV;



public abstract class AbstractResource implements WebDavResource{
	private final static Logger logger = LoggerFactory.getLogger(WebDavResource.class);
	protected String url;
	protected File resource;
	
	public AbstractResource() {
	}
	
	public AbstractResource(File resource, String url) {
		this.resource = resource;
		this.url = url;
	}
	
	@Override
	public javax.ws.rs.core.Response get(){
		logger.trace("Abstract - get(..)");
		return logResponse("GET", javax.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public javax.ws.rs.core.Response put(UriInfo uriInfo, final InputStream entityStream, final long contentLength) throws IOException {
		logRequest(uriInfo);
		return logResponse("PUT", javax.ws.rs.core.Response.status(501).build());
	}
	
	@Override
	public javax.ws.rs.core.Response mkcol(){
		logger.trace("Abstract - mkcol(..)");
		return logResponse("MKCOL", javax.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public javax.ws.rs.core.Response propfind(final UriInfo uriInfo, final int depth, final InputStream entityStream, final long contentLength, final Providers providers, final HttpHeaders httpHeaders) throws IOException {
		logRequest(uriInfo);
		return logResponse("PROPFIND", javax.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public javax.ws.rs.core.Response proppatch(){
		logger.trace("Abstract - proppatch(..)");
		return logResponse("PROPPATCH", javax.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public javax.ws.rs.core.Response copy(){
		logger.trace("Abstract - copy(..)");
		return logResponse("COPY", javax.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public javax.ws.rs.core.Response move(UriInfo uriInfo, String overwriteStr, String destination) throws URISyntaxException{
		logRequest(uriInfo);
		return logResponse("MOVE", javax.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public javax.ws.rs.core.Response delete(){
		logger.trace("Abstract - delete(..)");
		return logResponse("DELETE", javax.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public javax.ws.rs.core.Response options(){
		logger.trace("Abstract - options(..)");
		ResponseBuilder builder = javax.ws.rs.core.Response.ok();//noContent();
		builder.header(DAV, "1");
		/*
		 * builder.header("Allow","");
		 * OPTIONS, GET, HEAD, DELETE, PROPPATCH, COPY, MOVE, LOCK, UNLOCK, PROPFIND, PUT
		 */
		builder.header("Allow","OPTIONS,GET,HEAD,POST,DELETE,PROPPATCH,PROPFIND,COPY,MOVE,PUT,MKCOL,LOCK,UNLOCK");
		
		builder.header("MS-Author-Via", "DAV");
		
		return logResponse("OPTIONS", builder.build());
	}
	
	@Override
	public Object findResource(final String res){
		logger.trace("Abstract - findResource(..) - "+res);
		String path = resource.getPath()+File.separator+res;
		File newResource = new File(path);
		String newUrl = url+"/"+res;
		
		if(newResource.exists()){
			if(newResource.isDirectory()){
				logger.trace("Abstract - findResource(..) - isDirectory");
				return new DirectoryResource(newResource, newUrl);
			}else{
				logger.trace("Abstract - findResource(..) - isFile");
				return new FileResource(newResource, newUrl);
			}
		}else{
			return new UnknownResource(newResource, newUrl);
		}
	}

	@Override
	public Response lock(UriInfo uriInfo) {
		logRequest(uriInfo);
		URI uri = uriInfo.getRequestUri();
		LockDiscovery lockDiscovery =
				new LockDiscovery(new ActiveLock(LockScope.SHARED, LockType.WRITE, Depth.ZERO, new Owner(""), new TimeOut(75), new LockToken(new HRef(
						uri)), new LockRoot(new HRef(uri))));
		Prop prop = new Prop(lockDiscovery);
		return logResponse("LOCK", javax.ws.rs.core.Response.ok(prop)
				.header(DAV, "1")
				.build());
	}

	@Override
	public javax.ws.rs.core.Response unlock(UriInfo uriInfo, String token) {
		logRequest(uriInfo);
		logger.info("unlock({}, '{}' is ignored.", uriInfo.getRequestUri(), token);
		return logResponse("UNLOCK", javax.ws.rs.core.Response.serverError().status(501).header(DAV, "1").build());
	}

	protected static void logRequest(UriInfo info) {
		if (info instanceof WebApplicationContext) {
			logRequest((WebApplicationContext) info);
		} else {
			logger.info("<- {}", info.getRequestUri());
		}
	}

	// inspired by access-log from Tomcat
	private static void logRequest(WebApplicationContext ctx) {
		HttpRequestContext req = ctx.getRequest();
		HttpResponseContext resp = ctx.getResponse();
		logger.info("<- \"{} {}\" {} {} Content-Type=\"{}\" accept={} user-agent=\"{}\"",
				req.getMethod(), req.getPath(), resp.getStatus(), req.getHeaderValue("content-length"),
				req.getMediaType(), req.getAcceptableMediaTypes(), req.getHeaderValue("user-agent"));
		logHeaders(req.getRequestHeaders());
	}

	protected static Response logResponse(String method, Response resp) {
		logger.info("-> \"{}\" {}: {}", method, resp.getStatus(), resp.getEntity());
		logHeaders(resp.getMetadata());
		return resp;
	}

	private static void logHeaders(MultivaluedMap<String, ?> headers) {
		if (logger.isDebugEnabled()) {
			logger.debug("Headers:");
			for (String key : headers.keySet()) {
				logger.debug("\t{}={}", key, headers.get(key));
			}
		}
	}

}
