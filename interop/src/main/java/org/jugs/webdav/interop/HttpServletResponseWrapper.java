/* Copyright 2008, 2009 Daniel MANZKE
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
package org.jugs.webdav.interop;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * This class wraps a real HttpServletResponse and wraps the methods which are
 * responsible for setting the status code. It also catchs the OutputStream so
 * it could not be closed and send to the client.
 * 
 * @author Daniel Manzke
 */
public class HttpServletResponseWrapper extends
		javax.servlet.http.HttpServletResponseWrapper {
	private ByteArrayOutputStream output;

	public HttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new ByteArrayOutputStream();
	}

	public ServletOutputStream getOutputStream() {
		return new FilterServletStream(output);
	}

	public PrintWriter getWriter() {
		return new PrintWriter(getOutputStream(), true);
	}
	
	@Override
	public String toString() {
		return output.toString();
	}
}
