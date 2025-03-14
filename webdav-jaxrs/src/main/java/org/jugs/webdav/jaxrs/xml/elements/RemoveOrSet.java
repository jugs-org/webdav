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

import jakarta.xml.bind.annotation.XmlElement;

import org.jugs.webdav.util.Utilities;

/**
 * Internal superclass of set and remove WebDAV elements.<br>
 * 
 * This class shall not be used directly, but instead <code>Set</code> and <code>Remove</code> classes should be used.
 * 
 * @see Set
 * @see Remove
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public abstract class RemoveOrSet {

	@XmlElement
	private final Prop prop;

	public final Prop getProp() {
		return this.prop;
	}

	protected RemoveOrSet() {
		this.prop = null;
	}

	public RemoveOrSet(final Prop prop) {
		this.prop = Utilities.notNull(prop, "prop");
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this)
			return true;

		if (!(object instanceof RemoveOrSet))
			return false;

		final RemoveOrSet that = (RemoveOrSet) object;

		return this.prop.equals(that.prop);
	}

	@Override
	public final int hashCode() {
		return this.prop.hashCode();
	}

	@Override
	public String toString() {
		return Utilities.toString(this, this.prop);
	}
}
