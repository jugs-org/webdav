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

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.java.dev.webdav.jaxrs.ConstantsAdapter;
import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.elements.Rfc1123DateFormat;

/**
 * WebDAV getlastmodified Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getlastmodified">Chapter 15.7 "getlastmodified Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(GetLastModified.Adapter.class)
@XmlRootElement(name = "getlastmodified")
public final class GetLastModified {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final GetLastModified GETLASTMODIFIED = new GetLastModified();

	private Date dateTime;

	/**
	 * @deprecated Since 1.2. Use {@link #GETLASTMODIFIED} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
	public GetLastModified() {
		// For unmarshalling only.
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
	private final String getXmlValue() {
		return this.dateTime == null ? null : new Rfc1123DateFormat().format(this.dateTime);
	}

	@SuppressWarnings("unused")
	private final void setXmlValue(final String xmlValue) throws ParseException {
		this.dateTime = xmlValue == null || xmlValue.isEmpty() ? null : new Rfc1123DateFormat().parse(xmlValue);
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

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<GetLastModified> {
		@Override
		protected final Collection<GetLastModified> getConstants() {
			return singleton(GETLASTMODIFIED);
		}
	}
}
