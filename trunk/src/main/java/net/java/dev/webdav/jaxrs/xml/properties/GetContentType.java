/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2014 The java.net WebDAV Project
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

package net.java.dev.webdav.jaxrs.xml.properties;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * WebDAV getcontenttype Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getcontenttype">Chapter 15.5 "getcontenttype Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "getcontenttype")
public final class GetContentType {

	@XmlValue
	private final String mediaType;

	/**
	 * Creates an empty (thus <em>invalid</em>) instance. Use <em>only</em> to list property name within response to &lt;propname/&gt; request. Not to be used
	 * for creation of valid instances of this property; use {@link #GetContentType(String)} instead.
	 */
	public GetContentType() {
		this.mediaType = "";
	}

	public GetContentType(final String mediaType) {
		if (mediaType == null)
			throw new NullArgumentException("mediaType");

		this.mediaType = mediaType;
	}

	public final String getMediaType() {
		return this.mediaType;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof GetContentType))
			return false;

		final GetContentType that = (GetContentType) other;

		return this.mediaType.equals(that.mediaType);
	}

	@Override
	public final int hashCode() {
		return this.mediaType.hashCode();
	}
}
