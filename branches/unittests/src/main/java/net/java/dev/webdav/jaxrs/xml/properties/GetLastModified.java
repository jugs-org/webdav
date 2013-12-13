/*
 * Copyright 2008, 2009, 2012, 2013 Markus KARG
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

package net.java.dev.webdav.jaxrs.xml.properties;

import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.elements.Rfc1123DateFormat;

/**
 * WebDAV getlastmodified Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getlastmodified">Chapter 15.7 "getlastmodified Property" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "getlastmodified")
public final class GetLastModified {

	private Date dateTime;

	/**
	 * Creates an empty (thus <em>invalid</em>) instance. Use <em>only</em> to list property name within response to &lt;propname/&gt; request. Not to be used for creation of valid instances of this property; use {@link #GetLastModified(Date)} instead.
	 */
	public GetLastModified() {
		// Keeping defaults by intention.
	}

	public GetLastModified(final Date dateTime) {
		if (dateTime == null)
			throw new NullArgumentException("dateTime");

		this.dateTime = dateTime;
	}

	public final Date getDateTime() {
		return this.dateTime == null ? null : (Date) this.dateTime.clone();
	}

	@XmlValue
	private final String getXmlDateTime() {
		return this.dateTime == null ? null : new Rfc1123DateFormat().format(this.dateTime);
	}

	@SuppressWarnings("unused")
	private final void setXmlDateTime(final String rfc1123DateTime) throws ParseException {
		this.dateTime = rfc1123DateTime == null ? null : new Rfc1123DateFormat().parse(rfc1123DateTime);
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof GetLastModified))
			return false;

		final GetLastModified that = (GetLastModified) other;

		return this.dateTime == null && that.dateTime == null || this.dateTime != null && that.dateTime != null && this.dateTime.equals(that.dateTime);
	}

	@Override
	public final int hashCode() {
		return this.dateTime == null ? 0 : this.dateTime.hashCode();
	}
}
