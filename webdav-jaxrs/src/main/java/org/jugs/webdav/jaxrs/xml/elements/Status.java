/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package org.jugs.webdav.jaxrs.xml.elements;

import static jakarta.xml.bind.annotation.XmlAccessType.NONE;

import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

import org.jugs.webdav.jaxrs.ResponseStatus;
import org.jugs.webdav.util.Utilities;

/**
 * WebDAV status XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_status">Chapter 14.28 "status XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(NONE)
@XmlRootElement
public final class Status {

	@XmlValue
	private String statusLine;

	public final String getStatus() {
		return this.statusLine;
	}

	/**
	 * @deprecated Since 1.2. Construct immutable instance instead.
	 */
	@Deprecated
	public void setStatus(final String status) {
		this.statusLine = status;
	}

	@SuppressWarnings("unused")
	private Status() {
		// For unmarshalling only.
	}

	private Status(final int statusCode, final String reasonPhrase) {
		this.statusLine = String.format("HTTP/1.1 %d %s", statusCode, reasonPhrase);
	}

	/**
	 * @deprecated Since 1.1. Use {@link #Status(jakarta.ws.rs.core.Response.StatusType)} instead.
	 */
	@Deprecated
	public Status(final ResponseStatus responseStatus) {
		this(responseStatus.getStatusCode(), responseStatus.toString());
	}

	/**
	 * @deprecated Since 1.1. Use {@link #Status(jakarta.ws.rs.core.Response.StatusType)} instead.
	 */
	@Deprecated
	public Status(final Response.Status responseStatus) {
		this(responseStatus.getStatusCode(), responseStatus.toString());
	}

	public Status(final Response.StatusType responseStatus) {
		this(responseStatus.getStatusCode(), responseStatus.getReasonPhrase());
	}

	@Override
	public final boolean equals(final Object object) {
		if (object == this)
			return true;

		if (!(object instanceof Status))
			return false;

		final Status that = (Status) object;

		return this.statusLine.equals(that.statusLine);
	}

	@Override
	public final int hashCode() {
		return this.statusLine.hashCode();
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.statusLine);
	}
}
