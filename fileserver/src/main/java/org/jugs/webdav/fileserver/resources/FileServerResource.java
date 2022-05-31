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

import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static net.java.dev.webdav.jaxrs.Headers.DAV;
import static net.java.dev.webdav.jaxrs.xml.properties.ResourceType.COLLECTION;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Providers;

import net.java.dev.webdav.jaxrs.xml.elements.HRef;
import net.java.dev.webdav.jaxrs.xml.elements.MultiStatus;
import net.java.dev.webdav.jaxrs.xml.elements.Prop;
import net.java.dev.webdav.jaxrs.xml.elements.PropFind;
import net.java.dev.webdav.jaxrs.xml.elements.PropStat;
import net.java.dev.webdav.jaxrs.xml.elements.Response;
import net.java.dev.webdav.jaxrs.xml.elements.Status;
import net.java.dev.webdav.jaxrs.xml.properties.CreationDate;
import net.java.dev.webdav.jaxrs.xml.properties.DisplayName;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentLength;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentType;
import net.java.dev.webdav.jaxrs.xml.properties.GetLastModified;
import org.jugs.webdav.fileserver.FileServerApplication;


@Path(FileServerApplication.RESOURCE_NAME)
public class FileServerResource extends AbstractResource {
	public static String davFolder = System.getProperty("path", "./");

	public FileServerResource() {
		url = "";
		resource = new File(davFolder);
	}

	@Override
	public javax.ws.rs.core.Response propfind(final UriInfo uriInfo, final int depth, final InputStream entityStream, final long contentLength, final Providers providers, final HttpHeaders httpHeaders) throws URISyntaxException, IOException {
		URI uri = uriInfo.getRequestUri();

		logger.finer("FileSystem - propfind(..) " + uri + " depth - " + depth);
		System.out.println(uriInfo.getRequestUri().getHost());
		System.out.println(uriInfo.getBaseUri().toString());
		Prop prop = null;
		if(contentLength > 0){
			final MessageBodyReader<PropFind> reader = providers.getMessageBodyReader(PropFind.class, PropFind.class, new Annotation[0], APPLICATION_XML_TYPE);
			final PropFind entity = reader.readFrom(PropFind.class, PropFind.class, new Annotation[0], APPLICATION_XML_TYPE, httpHeaders.getRequestHeaders(),
					entityStream);
			prop = entity.getProp();
		}

		Date lastModified = new Date(resource.lastModified());
		final Response folder = new Response(
				new HRef(uri),
				null,
				null,
				null,
				new PropStat(new Prop(new CreationDate(lastModified),
						new GetLastModified(lastModified), COLLECTION), new Status(OK)));

		if (depth == 0) {
			return javax.ws.rs.core.Response.ok(new MultiStatus(folder))
					.build();
		}

		File[] files = resource.listFiles();
		List<Response> responses = new ArrayList<Response>();
		responses.add(folder);
		for (File file : files) {
			Response davFile;

			lastModified = new Date(file.lastModified());
			if (file.isDirectory()) {
				davFile = new Response(new HRef(uriInfo.getRequestUriBuilder().path(file.getName()).build()), null, null, null, new PropStat(new Prop(
						new CreationDate(lastModified), new GetLastModified(lastModified), COLLECTION, new DisplayName(file.getName())), new Status(OK)));

			} else {
				davFile = new Response(new HRef(uriInfo.getRequestUriBuilder().path(file.getName()).build()), null, null, null, new PropStat(new Prop(
						new CreationDate(lastModified), new GetLastModified(lastModified), new GetContentType("application/octet-stream"),
						new GetContentLength(file.length()), new DisplayName(file.getName())), new Status(OK)));

			}

			responses.add(davFile);
		}

		MultiStatus st = new MultiStatus(responses
				.toArray(new Response[responses.size()]));

		return javax.ws.rs.core.Response.ok(st).build();
	}
	
	@Override
	public javax.ws.rs.core.Response options(){
		logger.finer("FileSystem - options(..)");
		ResponseBuilder builder = javax.ws.rs.core.Response.ok();//noContent();
		builder.header(DAV, "1");
		/*
		 * builder.header("Allow","");
		 * OPTIONS, GET, HEAD, DELETE, PROPPATCH, COPY, MOVE, LOCK, UNLOCK, PROPFIND, PUT
		 */
		builder.header("Allow","OPTIONS,PROPFIND");
		
		builder.header("MS-Author-Via", "DAV");
		
		return builder.build();
	}
}