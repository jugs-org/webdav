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

import static net.java.dev.webdav.util.Utilities.sameOrEqual;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * WebDAV lockentry XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_lockentry">Chapter 14.10 "lockentry XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlType(propOrder = { "lockScope", "lockType" })
@XmlRootElement(name = "lockentry")
public final class LockEntry {

	@XmlElement(name = "lockscope")
	private LockScope lockScope;

	@XmlElement(name = "locktype")
	private LockType lockType;

	@SuppressWarnings("unused")
	private LockEntry() {
		// For unmarshalling only.
	}

	public LockEntry(final LockScope lockScope, final LockType lockType) {
		if (lockScope == null)
			throw new NullArgumentException("lockScope");

		if (lockType == null)
			throw new NullArgumentException("lockType");

		this.lockScope = lockScope;
		this.lockType = lockType;
	}

	public final LockScope getLockScope() {
		return this.lockScope;
	}

	public final LockType getLockType() {
		return this.lockType;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof LockEntry))
			return false;

		final LockEntry that = (LockEntry) other;

		return sameOrEqual(this.lockScope, that.lockScope) && sameOrEqual(this.lockType, that.lockType);
	}
}
