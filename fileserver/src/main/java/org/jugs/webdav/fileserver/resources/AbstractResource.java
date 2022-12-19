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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.LockDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.ext.Providers;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import static org.jugs.webdav.jaxrs.Headers.DAV;



public abstract class AbstractResource implements WebDavResource {
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
	public jakarta.ws.rs.core.Response get(UriInfo uriInfo) {
		logger.info("<- \"GET {}\"", uriInfo.getRequestUri());
		ResponseBuilder builder = Response.ok();
		builder.header("Content-Type", MediaType.TEXT_HTML);
		try {
			if (!resource.exists()) {
				logger.error("Resource '{}' does not exist (404).", resource);
				builder = Response.status(404);
				String html = MessageFormat.format(readResource("/static/404.html"), resource);
				builder.entity(html);
			} else if (resource.isDirectory()) {
				buildDirListing(uriInfo.getRequestUri(), builder);
			} else if (resource.isFile()) {
				builder = buildFileContent();
			} else {
				String html = MessageFormat.format(readResource("/static/index.html"), uriInfo.getRequestUri());
				builder.entity(html);
			}
		} catch (IOException ioe) {
			logger.error("No resource found:", ioe);
			builder = Response.status(404);
		}
		return logResponse("GET", builder.build());
	}

	private void buildDirListing(URI uri, ResponseBuilder builder) throws IOException {
		StringBuilder buf = new StringBuilder();
		for (File f : resource.listFiles()) {
			buf.append(MessageFormat.format("<li><a href={0}/{1}>{1}</a></li>", uri, f.getName()));
		}
		buf.append(MessageFormat.format("<li><a href={0}/..>..</a></li>", uri));
		String html = MessageFormat.format(readResource("/static/dir.html"),
				resource.getPath(),
				buf,
				uri);
		builder.entity(html);
	}

	private ResponseBuilder buildFileContent() throws IOException {
		ResponseBuilder builder = Response.ok();
		builder.header("Content-Type", MediaType.APPLICATION_OCTET_STREAM);
		byte[] content = FileUtils.readFileToByteArray(resource);
		builder.entity(content);
		return builder;
	}

	protected static String readResource(String name) throws IOException {
		try (InputStream istream = AbstractResource.class.getResourceAsStream(name)) {
			if (istream == null) {
				throw new FileNotFoundException(String.format("resource '%s' not found", name));
			}
			return IOUtils.toString(istream, StandardCharsets.UTF_8);
		}
	}
	
	@Override
	public jakarta.ws.rs.core.Response put(UriInfo uriInfo, final InputStream entityStream, final long contentLength) throws IOException {
		logRequest(uriInfo);
		return logResponse("PUT", jakarta.ws.rs.core.Response.status(501).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response mkcol(){
		logger.trace("Abstract - mkcol(..)");
		return logResponse("MKCOL", jakarta.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response propfind(final UriInfo uriInfo, final int depth, final InputStream entityStream, final long contentLength, final Providers providers, final HttpHeaders httpHeaders) throws IOException {
		logRequest(uriInfo);
		return logResponse("PROPFIND", jakarta.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response proppatch(){
		logger.trace("Abstract - proppatch(..)");
		return logResponse("PROPPATCH", jakarta.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response copy(){
		logger.trace("Abstract - copy(..)");
		return logResponse("COPY", jakarta.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response move(UriInfo uriInfo, String overwriteStr, String destination) throws URISyntaxException{
		logRequest(uriInfo);
		return logResponse("MOVE", jakarta.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response delete(){
		logger.trace("Abstract - delete(..)");
		return logResponse("DELETE", jakarta.ws.rs.core.Response.status(404).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response options(){
		logger.trace("Abstract - options(..)");
		ResponseBuilder builder = withDavHeader(jakarta.ws.rs.core.Response.ok());//noContent();
		/*
		 * builder.header("Allow","");
		 * OPTIONS, GET, HEAD, DELETE, PROPPATCH, COPY, MOVE, LOCK, UNLOCK, PROPFIND, PUT
		 */
		builder.header("Allow","OPTIONS,GET,HEAD,POST,DELETE,PROPPATCH,PROPFIND,COPY,MOVE,PUT,MKCOL,LOCK,UNLOCK");
		return logResponse("OPTIONS", builder.build());
	}

	protected ResponseBuilder withDavHeader(ResponseBuilder builder) {
		builder.header(DAV, "1,2,3");
		builder.header("MS-Author-Via", "DAV");
		return builder;
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
		ResponseBuilder builder = withDavHeader(Response.ok(prop));
		return logResponse("LOCK", builder.build());
	}

	@Override
	public jakarta.ws.rs.core.Response unlock(UriInfo uriInfo, String token) {
		logRequest(uriInfo);
		return logResponse("UNLOCK", withDavHeader(Response.noContent()).build());
	}

	protected static void logRequest(UriInfo info) {
		logger.info("<- {}", info.getRequestUri());
	}

	protected static void logRequest(String method, String context) {
		logger.debug("<- \"{} {}\"", method, context);
	}

	protected Response logResponse(String method, Response resp) {
		logger.info("-> \"{} {}: {}", method, url, resp.getStatus());
		logHeaders(resp.getMetadata());
		return resp;
	}

	private static void logHeaders(MultivaluedMap<String, ?> headers) {
		if (logger.isTraceEnabled()) {
			logger.trace("Headers:");
			for (String key : headers.keySet()) {
				logger.trace("\t{}={}", key, headers.get(key));
			}
		}
	}

}
