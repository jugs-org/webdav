/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2024 Java User Group Stuttgart
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

import static java.util.Collections.unmodifiableList;
import static org.jugs.webdav.util.Utilities.append;
import static org.jugs.webdav.util.Utilities.notNull;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.jugs.webdav.util.Utilities;

/**
 * WebDAV propertyupdate XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_propertyupdate">Chapter 14.19 "propertyupdate XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "propertyupdate")
public final class PropertyUpdate {

	@XmlElements({ @XmlElement(name = "remove", type = Remove.class), @XmlElement(name = "set", type = Set.class) })
	private final List<RemoveOrSet> removesOrSets;

	@SuppressWarnings("unused")
	private PropertyUpdate() {
		this.removesOrSets = new LinkedList<RemoveOrSet>();
	}

	public PropertyUpdate(final RemoveOrSet removeOrSet, final RemoveOrSet... removesOrSets) {
		this.removesOrSets = append(notNull(removeOrSet, "removeOrSet"), removesOrSets);
	}

	public final List<RemoveOrSet> list() {
		return unmodifiableList(this.removesOrSets);
	}

	@Override
	public final int hashCode() {
		return this.removesOrSets.hashCode();
	}

	@Override
	public final boolean equals(final Object o) {
		if (this == o)
			return true;

		if (!(o instanceof PropertyUpdate))
			return false;

		final PropertyUpdate that = (PropertyUpdate) o;

		return this.removesOrSets.equals(that.removesOrSets);
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.removesOrSets);
	}
}
