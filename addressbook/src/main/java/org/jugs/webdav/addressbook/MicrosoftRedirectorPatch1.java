/*
 * Copyright 2008, 2009 Markus KARG
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

package org.jugs.webdav.addressbook;

import static net.java.dev.webdav.jaxrs.Headers.DAV;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import net.java.dev.webdav.jaxrs.methods.OPTIONS;

/**
 * JAX-RS resource which is working around a bug in the Microsoft
 * WebDAV-Mini-Redirector.<br>
 * 
 * This particular client software expects a "DEV: 1" answer to an "OPTION /"
 * request at root level, even if the root level is not a WebDAV resource at
 * all.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 */
@Path("/")
public final class MicrosoftRedirectorPatch1 {

	@OPTIONS
	public final Response options() {
		return Response.noContent().header(DAV, "1").build();
	}

}
