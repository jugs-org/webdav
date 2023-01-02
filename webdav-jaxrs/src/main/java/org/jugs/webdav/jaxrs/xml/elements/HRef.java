/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2023 Java User Group Stuttgart
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

import static org.jugs.webdav.util.Utilities.notNull;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.jugs.webdav.util.Utilities;

/**
 * WebDAV href XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_href">Chapter 14.7 "href XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "href")
public final class HRef {

	@XmlValue
	private final String value;

	@SuppressWarnings("unused")
	private HRef() {
		this.value = null;
	}

	public HRef(final URI uri) {
		this.value = notNull(uri, "uri").toString();
	}

	public HRef(final String uri) {
		this.value = notNull(uri, "uri");
	}

	/**
	 * @return Value as a <code>URI</code> instance, if the content is a valid URI; <code>null</code> otherwise.
	 * @deprecated Since 1.1.1. Use {@link #getURI()} instead. Future releases will not contain this method anymore.
	 */
	@Deprecated
	public final URI getUri() {
		/*
		 * To preserve backwards compatibility, we must not throw an exception: Legacy code will not be prepared to catch it. Unfortunately this was a design
		 * fault in release 1.0 of this library that cannot be undone before release 2.0. The value is not a URI in all cases but may be any form of a relative
		 * reference, and such contain characters which would be illegal in an URI.
		 */
		try {
			return this.getURI();
		} catch (final URISyntaxException e) {
			return null;
		}
	}

	/**
	 * @return Value as a <code>URI</code> instance, if the value is a valid URI; <code>null</code> otherwise.
	 * @since 1.1.1
	 */
	public final URI getURI() throws URISyntaxException {
		return new URI(this.value);
	}

	/**
	 * @since 1.1.1
	 */
	public final String getValue() {
		return this.value;
	}

	@Override
	public final boolean equals(final Object other) {
		if (other == this)
			return true;

		if (!(other instanceof HRef))
			return false;

		final HRef that = (HRef) other;

		return this.value.equals(that.value);
	}

	@Override
	public final int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.value);
	}
}
