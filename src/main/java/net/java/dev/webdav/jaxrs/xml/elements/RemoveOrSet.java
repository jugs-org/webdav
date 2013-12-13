/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2013 The java.net WebDAV Project
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

package net.java.dev.webdav.jaxrs.xml.elements;

import javax.xml.bind.annotation.XmlElement;

import net.java.dev.webdav.jaxrs.NullArgumentException;

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
	private Prop prop;

	public final Prop getProp() {
		return this.prop;
	}

	protected RemoveOrSet() {
		// For unmarshalling only.
	}

	public RemoveOrSet(final Prop prop) {
		if (prop == null)
			throw new NullArgumentException("prop");

		this.prop = prop;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object == this)
			return true;

		if (!(object.getClass() == this.getClass()))
			return false;

		final RemoveOrSet that = (RemoveOrSet) object;

		return this.getProp().equals(that.getProp());
	}

	@Override
	public final int hashCode() {
		return this.getProp().hashCode();
	}
}
