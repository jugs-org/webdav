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

import static java.util.Objects.hash;
import static org.jugs.webdav.util.Utilities.array;
import static org.jugs.webdav.util.Utilities.notNull;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jugs.webdav.util.Utilities;

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
	private final LockScope lockScope;

	@XmlElement(name = "locktype")
	private final LockType lockType;

	@XmlElement
	private final Owner owner;

	@SuppressWarnings("unused")
	private LockInfo() {
		this.lockScope = null;
		this.lockType = null;
		this.owner = null;
	}

	public LockInfo(final LockScope lockScope, final LockType lockType, final Owner owner) {
		this.lockScope = notNull(lockScope, "lockScope");
		this.lockType = notNull(lockType, "lockType");
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
		return hash(this.lockScope, this.lockType, this.owner);
	}

	@Override
	public final boolean equals(final Object other) {
		if (other == this)
			return true;

		if (!(other instanceof LockInfo))
			return false;

		final LockInfo that = (LockInfo) other;

		return Arrays.equals(array(this.lockScope, this.lockType, this.owner), array(that.lockScope, that.lockType, that.owner));
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.lockScope, this.lockType, this.owner);
	}
}
