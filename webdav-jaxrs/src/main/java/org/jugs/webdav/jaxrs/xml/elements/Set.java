/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
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

import jakarta.xml.bind.annotation.XmlRootElement;

import org.jugs.webdav.util.Utilities;

/**
 * WebDAV set XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_set">Chapter 14.26 "set XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
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
	public final boolean equals(final Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Set))
			return false;

		return super.equals(o);
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.getProp());
	}
}
