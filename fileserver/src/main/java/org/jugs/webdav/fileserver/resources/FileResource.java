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

import static javax.ws.rs.core.Response.Status.OK;
import static org.jugs.webdav.jaxrs.Headers.DAV;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.logging.Level;
import org.slf4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Providers;

import org.jugs.webdav.jaxrs.xml.elements.HRef;
import org.jugs.webdav.jaxrs.xml.elements.MultiStatus;
import org.jugs.webdav.jaxrs.xml.elements.Prop;
import org.jugs.webdav.jaxrs.xml.elements.PropStat;
import org.jugs.webdav.jaxrs.xml.elements.Response;
import org.jugs.webdav.jaxrs.xml.elements.Rfc1123DateFormat;
import org.jugs.webdav.jaxrs.xml.elements.Status;
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLength;
import org.jugs.webdav.jaxrs.xml.properties.GetContentType;
import org.jugs.webdav.jaxrs.xml.properties.GetLastModified;
import org.jugs.webdav.fileserver.FileServerApplication;
import org.slf4j.LoggerFactory;


public class FileResource extends AbstractResource{

	private final static Logger logger = LoggerFactory.getLogger(FileResource.class);

	public FileResource(File resource, String url) {
		super(resource, url);
	}
	
//	@Override
	@GET
	@Produces("application/octet-stream")
	public javax.ws.rs.core.Response get() {
		logger.trace("File - get(..) " + url);
		if(!resource.exists()){
			return javax.ws.rs.core.Response.status(404).build();
		}else{
			ResponseBuilder builder = javax.ws.rs.core.Response.ok();
			InputStream in;
			try {
				in = new BufferedInputStream(new FileInputStream(resource));
			} catch (FileNotFoundException e) {
				return javax.ws.rs.core.Response.serverError().build();
			}
			builder.header("Last-Modified", new Rfc1123DateFormat().format(new Date(resource.lastModified())));
			builder.header("Content-Length", resource.length());

			return builder.entity(in).build();
		}
	}
	
	@Override
	public javax.ws.rs.core.Response delete() {
		boolean deleted = resource.delete();
		if(deleted){
			return javax.ws.rs.core.Response.noContent().build();
		}
		return super.delete();
	}
	
	@Override
	public javax.ws.rs.core.Response move(final UriInfo uriInfo, String overwriteStr, String destination) throws URISyntaxException {
		logger.trace("File - move(.."+overwriteStr+".."+destination+"..)");
		URI uri = uriInfo.getBaseUri();
		String host = uri.getScheme()+"://"+uri.getHost()+"/"+ FileServerApplication.RESOURCE_NAME+"/";
		String originalDestination = destination;
		try {
			destination = URLDecoder.decode(destination, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Cannot move {} to {}:", uri, destination, e);
		}
		destination = destination.replace(host, "");

		String root = FileServerResource.davFolder;
		File destFile = new File(root+File.separator+destination);
		boolean overwrite = overwriteStr.equalsIgnoreCase("T");
		
		if(destFile.equals(resource)){
			return javax.ws.rs.core.Response.status(403).build();
		}else{
			if(destFile.exists() && !overwrite){
				return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.PRECONDITION_FAILED).build();
			}
			if(!destFile.exists() || overwrite){
				destFile.delete();
				boolean moved = resource.renameTo(destFile);
				if(moved)
					return javax.ws.rs.core.Response.created(new URI(originalDestination)).build();
				else
					return javax.ws.rs.core.Response.serverError().build();
			}
			return javax.ws.rs.core.Response.status(409).build();
		}
	}
	
	@Override
	public javax.ws.rs.core.Response proppatch() {
		logger.trace("File - proppatch(..)");
		return super.proppatch();
	}
	
	@Override
	public javax.ws.rs.core.Response propfind(final UriInfo uriInfo, final int depth, final InputStream entityStream, final long contentLength, final Providers providers, final HttpHeaders httpHeaders) {
		logger.trace("File - propfind(..) " + uriInfo.getRequestUri() + " depth - "+depth+" = URL: "+url);

		Date lastModified = new Date(resource.lastModified());
		Response davFile = new Response(new HRef(uriInfo.getRequestUri()), null, null, null, new PropStat(new Prop(
					new CreationDate(lastModified), new GetLastModified(lastModified), new GetContentType("application/octet-stream"), new GetContentLength(resource.length())), new Status(OK)));

		MultiStatus st = new MultiStatus(davFile);

		return javax.ws.rs.core.Response.ok(st).build();
	}
	

	@Override
	public javax.ws.rs.core.Response put(final UriInfo uriInfo, final InputStream entityStream, final long contentLength) throws IOException {
		logger.trace("File - put(..) " + url);
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
		logger.trace("File - options(..)");
		ResponseBuilder builder = javax.ws.rs.core.Response.ok();//noContent();
		builder.header(DAV, "1");
		/*
		 * builder.header("Allow","");
		 * OPTIONS, GET, HEAD, DELETE, PROPPATCH, COPY, MOVE, LOCK, UNLOCK, PROPFIND, PUT
		 */
		builder.header("Allow","OPTIONS,GET,MOVE,PUT,PROPPATCH,PROPFIND");
		
		builder.header("MS-Author-Via", "DAV");
		
		return builder.build();
	}
}
