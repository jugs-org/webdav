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

import org.jugs.webdav.jaxrs.methods.MKCOL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.io.*;


public class UnknownResource extends AbstractResource {

	private final static Logger logger = LoggerFactory.getLogger(UnknownResource.class);

	public UnknownResource(File resource, String url) {
		super(resource, url);
	}
	
	@MKCOL
	public jakarta.ws.rs.core.Response mkcol(){
		logger.trace("mkcol(..folder..) - "+url);
		return logResponse("MKCOL", doMkcol());
	}

	private Response doMkcol() {
		if(resource.exists()){
			return Response.status(405).build();
		}else{
			boolean created = resource.mkdirs();
			if(created){
				return Response.status(201).build();
			}else{
				return Response.status(403).build();
			}
		}
	}

	@Override
	public jakarta.ws.rs.core.Response put(final UriInfo uriInfo, final InputStream entityStream, final long contentLength) throws IOException {
		logRequest(uriInfo);
		/*
		 * Workaround for Jersey issue #154 (see
		 * https://jersey.dev.java.net/issues/show_bug.cgi?id=154): Jersey will
		 * throw an exception and abstain from calling a method if the method
		 * expects a JAXB element body while the actual Content-Length is zero.
		 */

		if (contentLength == 0)
			return jakarta.ws.rs.core.Response.ok().build();

		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(resource));
		
		int b;
		while((b = entityStream.read()) != -1){
			out.write(b);
		}
		out.flush();
		out.close();

		/*
		 * End of #154 workaround
		 */

		logger.trace(String.format("STORED: %s", resource.getName()));
		return logResponse("PUT", jakarta.ws.rs.core.Response.created(uriInfo.getRequestUriBuilder().path(url).build()).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response options(){
		logger.trace("UnkownResource - options(..)");
		ResponseBuilder builder = withDavHeader(jakarta.ws.rs.core.Response.ok());//noContent();
		/*
		 * builder.header("Allow","");
		 * OPTIONS, GET, HEAD, DELETE, PROPPATCH, COPY, MOVE, LOCK, UNLOCK, PROPFIND, PUT
		 */
		builder.header("Allow","OPTIONS,MKCOL,PUT");
		return builder.build();
	}
}
