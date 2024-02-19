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

import org.jugs.webdav.fileserver.FileServerApplication;
import org.jugs.webdav.fileserver.tools.PropStatBuilderExt;
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.jugs.webdav.jaxrs.xml.properties.GetLastModified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Providers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.jugs.webdav.jaxrs.xml.properties.ResourceType.COLLECTION;


public class DirectoryResource extends AbstractResource {

	private final static Logger logger = LoggerFactory.getLogger(DirectoryResource.class.getName());
	public DirectoryResource(File resource, String url) {
		super(resource, url);
	}
	
	@Override
	public jakarta.ws.rs.core.Response move(final UriInfo uriInfo, String overwriteStr, String destination) throws URISyntaxException {
		logRequest("MOVE", uriInfo);
		URI uri = uriInfo.getBaseUri();
		String host = uri.getScheme()+"://"+uri.getHost()+"/"+ FileServerApplication.RESOURCE_NAME+"/";
		String originalDestination = destination;
		try {
			destination = URLDecoder.decode(destination, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("'{}' does not support UTF-8", destination, e);
		}
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
				destFile.delete();
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
	public jakarta.ws.rs.core.Response propfind(final UriInfo uriInfo, final int depth, final InputStream entityStream, final long contentLength, final Providers providers, final HttpHeaders httpHeaders) throws IOException{
		logRequest("PROPFIND", uriInfo);
		if(!resource.exists()){
			return logResponse("PROPFIND", uriInfo, jakarta.ws.rs.core.Response.status(404).build());
		}

		Prop prop = null;
		if(contentLength > 0){
			final MessageBodyReader<PropFind> reader = providers.getMessageBodyReader(PropFind.class, PropFind.class, new Annotation[0], APPLICATION_XML_TYPE);
			final PropFind entity = reader.readFrom(PropFind.class, PropFind.class, new Annotation[0], APPLICATION_XML_TYPE, httpHeaders.getRequestHeaders(),
					entityStream);
			prop = entity.getProp();
		}

		Date lastModified = new Date(resource.lastModified());
		final Response davResource = new Response(
				new HRef(uriInfo.getRequestUri()),
				null,
				null,
				null,
				new PropStat(new Prop(new CreationDate(lastModified), new GetLastModified(lastModified), COLLECTION), new Status(OK)));

		return logResponse("PROPFIND", uriInfo, propfind(uriInfo, depth, prop, davResource));
	}

	private jakarta.ws.rs.core.Response propfind(UriInfo uriInfo, int depth, Prop prop, Response davResource) {
		Date lastModified;
		if (depth == 0) {
			return jakarta.ws.rs.core.Response.ok(new MultiStatus(davResource))
					.build();
		}

		if(resource != null && resource.isDirectory()){
			File[] files = resource.listFiles();
			List<Response> responses = new ArrayList<>();
			responses.add(davResource);
			for (File file : files) {
				Response davFile;

				lastModified = new Date(file.lastModified());
				String fileName = file.getName();
				PropStatBuilderExt props = new PropStatBuilderExt();
				props.lastModified(lastModified).creationDate(lastModified).displayName(fileName).status(OK);

				if (file.isDirectory()) {
					props.isCollection();
				} else {
					props.isResource(file.length(), "application/octet-stream");
				}

				PropStat found = props.build();
				PropStat notFound = null;
				if(prop != null){
//					props.isHidden(false);
//					props.lastAccessed(lastModified);
					notFound = props.notFound(prop);
				}

				if(notFound != null)
					davFile = new Response(new HRef(uriInfo.getRequestUriBuilder().path(fileName).build()), null, null, null, found, notFound);
				else
					davFile = new Response(new HRef(uriInfo.getRequestUriBuilder().path(fileName).build()), null, null, null, found);

				responses.add(davFile);
			}

			MultiStatus st = new MultiStatus(responses
					.toArray(new Response[responses.size()]));

			return jakarta.ws.rs.core.Response.ok(st).build();
		}

		return jakarta.ws.rs.core.Response.noContent().build();
	}

}