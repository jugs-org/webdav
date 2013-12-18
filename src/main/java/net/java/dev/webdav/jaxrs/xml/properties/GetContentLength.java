/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2013 The java.net WebDAV Project
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

import static net.java.dev.webdav.util.Utilities.sameOrEqual;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * WebDAV getcontentlength Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getcontentlength">Chapter 15.4 "getcontentlength Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "getcontentlength")
public final class GetContentLength {

	@XmlValue
	private Long contentLength;

	/**
	 * Creates an empty (thus <em>invalid</em>) instance. Use <em>only</em> to list property name within response to &lt;propname/&gt; request. Not to be used
	 * for creation of valid instances of this property; use {@link #GetContentLength(long)} instead.
	 */
	public GetContentLength() {
		// Has no members.
	}

	public GetContentLength(final long contentLength) {
		this.contentLength = contentLength;
	}

	/**
	 * @deprecated Since 1.2 use {@link #getContentLength()} instead. This method will not be supported anymore in future releases.
	 * @return The same result as {@link #getContentLength()}
	 */
	@Deprecated
	public final long getLanguageTag() {
		return this.getContentLength();
	}

	public final long getContentLength() {
		return this.contentLength == null ? 0 : this.contentLength;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof GetContentLength))
			return false;

		final GetContentLength that = (GetContentLength) other;

		return sameOrEqual(this.contentLength, that.contentLength);
	}

	@Override
	public final int hashCode() {
		return this.contentLength == null ? 0 : this.contentLength.hashCode();
	}
}
