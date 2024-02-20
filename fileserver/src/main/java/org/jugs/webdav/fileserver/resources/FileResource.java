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

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Providers;
import org.jugs.webdav.fileserver.FileServerApplication;
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLength;
import org.jugs.webdav.jaxrs.xml.properties.GetContentType;
import org.jugs.webdav.jaxrs.xml.properties.GetLastModified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static jakarta.ws.rs.core.Response.Status.OK;


public class FileResource extends AbstractResource {

	private final static Logger logger = LoggerFactory.getLogger(FileResource.class);

	public FileResource(File resource, String url) {
		super(resource, url);
	}
	
//	@Override
	@GET
	@Produces("application/octet-stream")
	public jakarta.ws.rs.core.Response get() {
		logRequest("GET", url);
		if(!resource.exists()) {
			return jakarta.ws.rs.core.Response.status(404).build();
		} else {
			ResponseBuilder builder = jakarta.ws.rs.core.Response.ok();
			InputStream in;
			try {
				in = new BufferedInputStream(new FileInputStream(resource));
			} catch (FileNotFoundException e) {
				return jakarta.ws.rs.core.Response.serverError().build();
			}
			builder.header("Last-Modified", new Rfc1123DateFormat().format(new Date(resource.lastModified())));
			builder.header("Content-Length", resource.length());

			return logResponse("GET", builder.entity(in).build());
		}
	}
	
	@Override
	public jakarta.ws.rs.core.Response delete() {
		logRequest("DELETE", url);
		boolean deleted = resource.delete();
		if(deleted){
			return logResponse("DELETE", jakarta.ws.rs.core.Response.noContent().build());
		}
		return super.delete();
	}
	
	@Override
	public jakarta.ws.rs.core.Response move(final UriInfo uriInfo, String overwriteStr, String destination) throws URISyntaxException {
		logRequest("MOVE", uriInfo);
		URI uri = uriInfo.getBaseUri();
		String host = uri.getScheme()+"://"+uri.getHost()+"/"+ FileServerApplication.RESOURCE_NAME+"/";
		String originalDestination = destination;
		destination = URLDecoder.decode(destination, StandardCharsets.UTF_8);
		destination = destination.replace(host, "");

		String root = FileServerResource.davFolder;
		File destFile = new File(root+File.separator+destination);
		boolean overwrite = overwriteStr.equalsIgnoreCase("T");

		return logResponse("MOVE", uriInfo, move(originalDestination, destFile, overwrite));
	}

	private jakarta.ws.rs.core.Response move(String originalDestination, File destFile, boolean overwrite)
			throws URISyntaxException {
		if(destFile.equals(resource)){
			return jakarta.ws.rs.core.Response.status(403).build();
		}else{
			if(destFile.exists() && !overwrite){
				return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
			}
			if(!destFile.exists() || overwrite){
				if (destFile.delete()) {
					logger.info("{} was deleted.", destFile);
				}
				boolean moved = resource.renameTo(destFile);
				if(moved)
					return jakarta.ws.rs.core.Response.created(new URI(originalDestination)).build();
				else
					return jakarta.ws.rs.core.Response.serverError().build();
			}
			return jakarta.ws.rs.core.Response.status(409).build();
		}
	}

	@Override
	public jakarta.ws.rs.core.Response propfind(final UriInfo uriInfo, final int depth, final InputStream entityStream, final long contentLength, final Providers providers, final HttpHeaders httpHeaders) {
		logRequest("PROPFIND", uriInfo);

		Date lastModified = new Date(resource.lastModified());
		Response davFile = new Response(new HRef(uriInfo.getRequestUri()), null, null, null, new PropStat(new Prop(
				new CreationDate(lastModified), new GetLastModified(lastModified),
				new GetContentType("application/octet-stream"), new GetContentLength(resource.length())),
				new Status(OK)));

		MultiStatus st = new MultiStatus(davFile);

		return logResponse("PROPFIND", uriInfo, jakarta.ws.rs.core.Response.ok(st).build());
	}
	

	@Override
	public jakarta.ws.rs.core.Response put(final UriInfo uriInfo, final InputStream entityStream, final long contentLength) throws IOException {
		logRequest("PUT", uriInfo);
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
		return logResponse("PUT", uriInfo, jakarta.ws.rs.core.Response.created(uriInfo.getRequestUriBuilder().path(url).build()).build());
	}
	
	@Override
	public jakarta.ws.rs.core.Response options(){
		logger.trace("File - options(..)");
		ResponseBuilder builder = withDavHeader(jakarta.ws.rs.core.Response.ok());//noContent();
		/*
		 * builder.header("Allow","");
		 * OPTIONS, GET, HEAD, DELETE, PROPPATCH, COPY, MOVE, LOCK, UNLOCK, PROPFIND, PUT
		 */
		builder.header("Allow","OPTIONS,GET,MOVE,PUT,PROPPATCH,PROPFIND");
		return logResponse("OPTIONS", builder.build());
	}
}
