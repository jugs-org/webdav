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

import static java.util.Collections.singleton;
import static javax.xml.bind.annotation.XmlAccessType.NONE;
import static net.java.dev.webdav.util.Utilities.sameOrEqual;

import java.util.Collection;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.java.dev.webdav.jaxrs.ConstantsAdapter;

/**
 * WebDAV getcontentlength Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getcontentlength">Chapter 15.4 "getcontentlength Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(NONE)
@XmlJavaTypeAdapter(GetContentLength.Adapter.class)
@XmlRootElement(name = "getcontentlength")
public final class GetContentLength {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final GetContentLength GETCONTENTLENGTH = new GetContentLength();

	private Long contentLength;

	@SuppressWarnings("unused")
	private final String getXmlValue() {
		return this.contentLength == null ? null : Long.toString(this.contentLength);
	}

	@XmlValue
	private final void setXmlValue(final String xmlValue) {
		this.contentLength = xmlValue == null || xmlValue.isEmpty() ? null : Long.parseLong(xmlValue);
	}

	/**
	 * @deprecated Since 1.2. Use {@link #GETCONTENTLENGTH} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
	public GetContentLength() {
		// For unmarshalling only
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
		return Objects.hashCode(this.contentLength);
	}

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<GetContentLength> {
		@Override
		protected final Collection<GetContentLength> getConstants() {
			return singleton(GETCONTENTLENGTH);
		}
	}
}
