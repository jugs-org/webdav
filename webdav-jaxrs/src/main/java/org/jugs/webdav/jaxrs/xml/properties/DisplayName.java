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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jugs.webdav.jaxrs.ConstantsAdapter;
import org.jugs.webdav.util.Utilities;

/**
 * WebDAV displayname Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_displayname">Chapter 15.2 "displayname Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(DisplayName.Adapter.class)
@XmlRootElement(name = "displayname")
public final class DisplayName {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final DisplayName DISPLAYNAME = new DisplayName();

	@XmlValue
	private final String name;

	/**
	 * @deprecated Since 1.2. Use {@link #DISPLAYNAME} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
	public DisplayName() {
		this.name = "";
	}

	public DisplayName(final String name) {
		this.name = notNull(name, "name");
	}

	public final String getName() {
		return this.name;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof DisplayName))
			return false;

		final DisplayName that = (DisplayName) other;

		return this.name.equals(that.name);
	}

	@Override
	public final int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<DisplayName> {
		@Override
		protected final Collection<DisplayName> getConstants() {
			return singleton(DISPLAYNAME);
		}
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.name);
	}
}
