/*
 * Copyright 2008, 2009 Markus KARG
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

import static net.java.dev.webdav.util.Utilities.sameOrEqual;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * WebDAV displayname Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_displayname">Chapter 15.2 "displayname Property" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "displayname")
public final class DisplayName {

	@XmlValue
	private String name;

	public DisplayName() {
		// Has no members.
	}

	public DisplayName(final String name) {
		this.name = name;
	}

	public final String getName() {
		return this.name;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof DisplayName))
			return false;

		final DisplayName that = (DisplayName) other;

		return sameOrEqual(this.name, that.name);
	}

	@Override
	public final int hashCode() {
		return this.name == null ? 0 : this.name.hashCode();
	}

	@SuppressWarnings("unused")
	private final void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
		if (this.name != null && this.name.length() == 0)
			this.name = null;
	}
}
