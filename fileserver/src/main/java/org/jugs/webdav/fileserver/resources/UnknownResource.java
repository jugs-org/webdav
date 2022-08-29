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

import static org.jugs.webdav.jaxrs.Headers.DAV;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import org.slf4j.Logger;

import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jugs.webdav.jaxrs.methods.MKCOL;
import org.slf4j.LoggerFactory;


public class UnknownResource extends AbstractResource {

	private final static Logger logger = LoggerFactory.getLogger(UnknownResource.class);

	public UnknownResource(File resource, String url) {
		super(resource, url);
	}
	
	@MKCOL
	public javax.ws.rs.core.Response mkcol(){
		logger.trace("mkcol(..folder..) - "+url);
					
		if(resource.exists()){
			return javax.ws.rs.core.Response.status(405).build();
		}else{
			boolean created = resource.mkdirs();
			if(created){
				return javax.ws.rs.core.Response.status(201).build();	
			}else{
				return javax.ws.rs.core.Response.status(403).build();
			}
		}
	}
	
	@Override
	public javax.ws.rs.core.Response put(final UriInfo uriInfo, final InputStream entityStream, final long contentLength) throws IOException {
		logger.trace("PUT: " + url);
		/*
		 * Workaround for Jersey issue #154 (see
		 * https://jersey.dev.java.net/issues/show_bug.cgi?id=154): Jersey will
		 * throw an exception and abstain from calling a method if the method
		 * expects a JAXB element body while the actual Content-Length is zero.
		 */

		if (contentLength == 0)
			return javax.ws.rs.core.Response.ok().build();

		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(resource));
		
		int b = -1;
		while((b = entityStream.read()) != -1){
			out.write(b);
		}
		out.flush();
		out.close();

		/*
		 * End of #154 workaround
		 */

		logger.trace(String.format("STORED: %s", resource.getName()));
		return javax.ws.rs.core.Response.created(uriInfo.getRequestUriBuilder().path(url).build()).build();
	}
	
	@Override
	public javax.ws.rs.core.Response options(){
		logger.trace("UnkownResource - options(..)");
		ResponseBuilder builder = javax.ws.rs.core.Response.ok();//noContent();
		builder.header(DAV, "1");
		/*
		 * builder.header("Allow","");
		 * OPTIONS, GET, HEAD, DELETE, PROPPATCH, COPY, MOVE, LOCK, UNLOCK, PROPFIND, PUT
		 */
		builder.header("Allow","OPTIONS,MKCOL,PUT");
		
		builder.header("MS-Author-Via", "DAV");
		
		return builder.build();
	}
}
