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
 * WebDAV lockinfo XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_lockinfo">Chapter 14.11 "lockinfo XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlType(propOrder = { "lockScope", "lockType", "owner" })
@XmlRootElement(name = "lockinfo")
public final class LockInfo {

	@XmlElement(name = "lockscope")
	private LockScope lockScope;

	@XmlElement(name = "locktype")
	private LockType lockType;

	@XmlElement
	private Owner owner;

	@SuppressWarnings("unused")
	private LockInfo() {
		// For unmarshalling only.
	}

	public LockInfo(final LockScope lockScope, final LockType lockType, final Owner owner) {
		if (lockScope == null)
			throw new NullArgumentException("lockScope");

		if (lockType == null)
			throw new NullArgumentException("lockType");

		this.lockScope = lockScope;
		this.lockType = lockType;
		this.owner = owner;
	}

	public final LockScope getLockScope() {
		return this.lockScope;
	}

	public final LockType getLockType() {
		return this.lockType;
	}

	public final Owner getOwner() {
		return this.owner;
	}

	@Override
	public final int hashCode() {
		return this.lockScope.hashCode() ^ this.lockType.hashCode() ^ (this.owner == null ? -1 : this.owner.hashCode());
	}

	@Override
	public final boolean equals(final Object other) {
		if (other == this)
			return true;

		if (!(other instanceof LockInfo))
			return false;

		final LockInfo that = (LockInfo) other;

		return this.lockScope.equals(that.lockScope) && this.lockType.equals(that.lockType) && sameOrEqual(this.owner, that.owner);
	}
}
