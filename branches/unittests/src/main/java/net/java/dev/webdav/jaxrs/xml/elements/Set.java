/*
 * Copyright 2008, 2009, 2012 Markus KARG
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

package net.java.dev.webdav.jaxrs.xml.elements;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * WebDAV set XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_set">Chapter 14.26 "set XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement
public final class Set extends RemoveOrSet {

	@SuppressWarnings("unused")
	private Set() {
		// For unmarshalling only.
	}

	public Set(final Prop prop) {
		super(prop);
	}

	@Override
	public final String toString() {
		return String.format("Set (%s)", super.getProp());
	}

	@Override
	public final boolean equals(final Object object) {
		if (object == this)
			return true;

		if (!(object instanceof Set))
			return false;

		final Set that = (Set) object;

		return this.getProp().equals(that.getProp());
	}
}
