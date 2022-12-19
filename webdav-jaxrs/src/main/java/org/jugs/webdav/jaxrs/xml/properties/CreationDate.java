/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2022 Java User Group Stuttgart
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

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jugs.webdav.jaxrs.ConstantsAdapter;
import org.jugs.webdav.jaxrs.xml.elements.Rfc3339DateTimeFormat;
import org.jugs.webdav.util.Utilities;

/**
 * WebDAV creationdate Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_creationdate">Chapter 15.1 "creationdate Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(CreationDate.Adapter.class)
@XmlRootElement(name = "creationdate")
public final class CreationDate {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final CreationDate CREATIONDATE = new CreationDate();

	private Date dateTime;

	/**
	 * @deprecated Since 1.2. Use {@link #CREATIONDATE} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
	public CreationDate() {
		// For unmarshalling only.
	}

	public CreationDate(final Date dateTime) {
		this.dateTime = notNull(dateTime, "dateTime");
	}

	public final Date getDateTime() {
		return this.dateTime == null ? null : (Date) this.dateTime.clone();
	}

	@XmlValue
	private final String getXmlValue() {
		return this.dateTime == null ? null : new Rfc3339DateTimeFormat().format(this.dateTime);
	}

	@SuppressWarnings("unused")
	private final void setXmlValue(final String xmlValue) throws ParseException {
		this.dateTime = xmlValue == null || xmlValue.isEmpty() ? null : new Rfc3339DateTimeFormat().parse(xmlValue);
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof CreationDate))
			return false;

		final CreationDate that = (CreationDate) other;

		return Objects.equals(this.dateTime, that.dateTime);
	}

	@Override
	public final int hashCode() {
		return Objects.hashCode(this.dateTime);
	}

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<CreationDate> {
		@Override
		protected final Collection<CreationDate> getConstants() {
			return singleton(CREATIONDATE);
		}
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.dateTime);
	}
}
