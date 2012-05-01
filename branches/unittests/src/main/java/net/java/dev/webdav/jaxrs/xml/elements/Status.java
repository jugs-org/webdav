/*
 * Copyright 2008, 2009, 2012 Markus KARG
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

package net.java.dev.webdav.jaxrs.xml.elements;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import net.java.dev.webdav.jaxrs.ResponseStatus;

/**
 * WebDAV status XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_status">Chapter 14.28 "status XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(NONE)
@XmlRootElement
public final class Status {

	@XmlValue
	protected String statusLine;

	public final String getStatus() {
		return this.statusLine;
	}

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
	 * @deprecated Since 1.1. Use {@link #Status(javax.ws.rs.core.Response.StatusType)} instead.
	 */
	@Deprecated
	public Status(final ResponseStatus responseStatus) {
		this(responseStatus.getStatusCode(), responseStatus.toString());
	}

	/**
	 * @deprecated Since 1.1. Use {@link #Status(javax.ws.rs.core.Response.StatusType)} instead.
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
}
