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

package org.jugs.webdav.jaxrs.xml.elements;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jugs.webdav.util.Utilities;

/**
 * WebDAV prop XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_prop">Chapter 14.18 "prop XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement
public final class Prop {

	@XmlAnyElement(lax = true)
	private final List<Object> properties;

	@SuppressWarnings("unused")
	private Prop() {
		// For unmarshalling only.
		this.properties = new LinkedList<Object>();
	}

	public Prop(final Object... any) {
		this.properties = asList(any);
	}

	public final List<Object> getProperties() {
		return unmodifiableList(this.properties);
	}

	@Override
	public final int hashCode() {
		return this.properties.hashCode();
	}

	@Override
	public final boolean equals(final Object object) {
		if (object == this)
			return true;

		if (!(object instanceof Prop))
			return false;

		final Prop that = (Prop) object;

		return this.properties.equals(that.properties);
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.properties);
	}
}
