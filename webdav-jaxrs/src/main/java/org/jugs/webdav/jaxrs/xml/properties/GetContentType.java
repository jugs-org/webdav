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

package org.jugs.webdav.jaxrs.xml.properties;

import static java.util.Collections.singleton;
import static org.jugs.webdav.util.Utilities.notNull;

import java.util.Collection;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jugs.webdav.jaxrs.ConstantsAdapter;
import org.jugs.webdav.util.Utilities;

/**
 * WebDAV getcontenttype Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getcontenttype">Chapter 15.5 "getcontenttype Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(GetContentType.Adapter.class)
@XmlRootElement(name = "getcontenttype")
public final class GetContentType {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final GetContentType GETCONTENTTYPE = new GetContentType();

	@XmlValue
	private final String mediaType;

	/**
	 * @deprecated Since 1.2. Use {@link #GETCONTENTTYPE} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
	public GetContentType() {
		this.mediaType = "";
	}

	public GetContentType(final String mediaType) {
		this.mediaType = notNull(mediaType, "mediaType");
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

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<GetContentType> {
		@Override
		protected final Collection<GetContentType> getConstants() {
			return singleton(GETCONTENTTYPE);
		}
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.mediaType);
	}
}
