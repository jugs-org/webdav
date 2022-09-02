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
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Providers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.jugs.webdav.jaxrs.xml.properties.ResourceType.COLLECTION;


@Path(FileServerApplication.RESOURCE_NAME)
public class FileServerResource extends AbstractResource {

	private final static Logger logger = LoggerFactory.getLogger(FileServerResource.class);

	public static String davFolder = System.getProperty("path", "./");

	public FileServerResource() {
		url = "";
		resource = new File(davFolder);
	}

	@Override
	public javax.ws.rs.core.Response propfind(final UriInfo uriInfo, final int depth, final InputStream entityStream, final long contentLength, final Providers providers, final HttpHeaders httpHeaders) throws IOException {
		logRequest(uriInfo);
		URI uri = uriInfo.getRequestUri();

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

		return logResponse("PROPFIND", propfind(uriInfo, depth, folder));
	}

	private javax.ws.rs.core.Response propfind(UriInfo uriInfo, int depth, Response folder) {
		Date lastModified;
		if (depth == 0) {
			return javax.ws.rs.core.Response.ok(new MultiStatus(folder))
					.build();
		}

		File[] files = resource.listFiles();
		List<Response> responses = new ArrayList<>();
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

}