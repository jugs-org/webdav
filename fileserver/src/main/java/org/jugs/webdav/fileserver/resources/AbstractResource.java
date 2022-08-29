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

import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.LockDiscovery;

import static org.jugs.webdav.jaxrs.Headers.DAV;
import static org.jugs.webdav.jaxrs.Headers.LOCK_TOKEN;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Providers;



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
		return javax.ws.rs.core.Response.status(404).build();
	}
	
	@Override
	public javax.ws.rs.core.Response put(UriInfo uriInfo, final InputStream entityStream, final long contentLength) throws IOException {
		logger.trace("Abstract - put(..)");
		return javax.ws.rs.core.Response.status(501).build();
	}
	
	@Override
	public javax.ws.rs.core.Response mkcol(){
		logger.trace("Abstract - mkcol(..)");
		return javax.ws.rs.core.Response.status(404).build();
	}
	
	@Override
	public javax.ws.rs.core.Response propfind(final UriInfo uriInfo, final int depth, final InputStream entityStream, final long contentLength, final Providers providers, final HttpHeaders httpHeaders) throws IOException {
		logger.trace("Abstract - propfind(..) - "+uriInfo.getRequestUri());
		return javax.ws.rs.core.Response.status(404).build();
	}
	
	@Override
	public javax.ws.rs.core.Response proppatch(){
		logger.trace("Abstract - proppatch(..)");
		return javax.ws.rs.core.Response.status(404).build();
	}
	
	@Override
	public javax.ws.rs.core.Response copy(){
		logger.trace("Abstract - copy(..)");
		return javax.ws.rs.core.Response.status(404).build();
	}
	
	@Override
	public javax.ws.rs.core.Response move(UriInfo uriInfo, String overwriteStr, String destination) throws URISyntaxException{
		logger.trace("Abstract - move(..)");
		return javax.ws.rs.core.Response.status(404).build();
	}
	
	@Override
	public javax.ws.rs.core.Response delete(){
		logger.trace("Abstract - delete(..)");
		return javax.ws.rs.core.Response.status(404).build();
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
		builder.header("Allow","OPTIONS,GET,HEAD,DELETE,PROPPATCH,PROPFIND,COPY,MOVE,PUT,MKCOL,LOCK");
		
		builder.header("MS-Author-Via", "DAV");
		
		return builder.build();
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
	public Object lock(UriInfo uriInfo) {
		URI uri = uriInfo.getRequestUri();
		logger.info(String.format("Lock for %s required.", uri));
		LockDiscovery lockDiscovery =
				new LockDiscovery(new ActiveLock(LockScope.SHARED, LockType.WRITE, Depth.ZERO, new Owner(""), new TimeOut(75), new LockToken(new HRef(
						uri)), new LockRoot(new HRef(uri))));
		Prop prop = new Prop(lockDiscovery);
		return javax.ws.rs.core.Response.ok(prop)
				.header(DAV, "1")
				.build();
	}

}

