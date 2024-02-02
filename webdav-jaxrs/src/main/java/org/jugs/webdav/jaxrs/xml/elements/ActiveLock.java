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

import org.jugs.webdav.util.Utilities;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Arrays;

import static java.util.Objects.hash;
import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import static org.jugs.webdav.util.Utilities.array;
import static org.jugs.webdav.util.Utilities.notNull;

/**
 * WebDAV activelock XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_activelock">Chapter 14.1 "activelock XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = { "lockScope", "lockType", "depth", "owner", "timeOut", "lockToken", "lockRoot" })
@XmlRootElement(name = "activelock")
public final class ActiveLock {

	@XmlElement(name = "lockscope")
	private final LockScope lockScope;

	@XmlElement(name = "locktype")
	private final LockType lockType;

	private final Depth depth;

	private final Owner owner;

	@XmlElement(name = "timeout")
	private final TimeOut timeOut;

	@XmlElement(name = "locktoken")
	private final LockToken lockToken;

	@XmlElement(name = "lockroot")
	private final LockRoot lockRoot;

	@SuppressWarnings("unused")
	private ActiveLock() {
		this.lockScope = null;
		this.lockType = null;
		this.depth = null;
		this.owner = null;
		this.timeOut = null;
		this.lockToken = null;
		this.lockRoot = null;
	}

	public ActiveLock(final LockScope lockScope, final LockType lockType, final Depth depth, final Owner owner, final TimeOut timeOut,
			final LockToken lockToken, final LockRoot lockRoot) {
		this.lockScope = notNull(lockScope, "lockScope");
		this.lockType = notNull(lockType, "lockType");
		this.depth = notNull(depth, "depth");
		this.owner = owner;
		this.timeOut = timeOut;
		this.lockToken = lockToken;
		this.lockRoot = notNull(lockRoot, "lockRoot");
	}

	/**
	 * Get the lock scope.
	 *
	 * @return the lock scope.
	 */
	public LockScope getLockScope() {
		return this.lockScope;
	}

	/**
	 * Get the lock type.
	 *
	 * @return the lock type.
	 */
	public LockType getLockType() {
		return this.lockType;
	}

	/**
	 * Get the depth.
	 *
	 * @return the depth
	 * @deprecated Since 1.1.1. Use {@link #getDepth()} instead.
	 */
	@Deprecated
	public Depth depth() {
		return this.depth;
	}

	/**
	 * Get the depth.
	 *
	 * @return the depth
	 * @since 1.1.1
	 */
	public Depth getDepth() {
		return this.depth;
	}

	/**
	 * Get the owner.
	 *
	 * @return the owner
	 */
	public Owner getOwner() {
		return this.owner;
	}

	/**
	 * Get the timout.
	 *
	 * @return the timeout.
	 */
	public TimeOut getTimeOut() {
		return this.timeOut;
	}

	public LockToken getLockToken() {
		return this.lockToken;
	}

	public LockRoot getLockRoot() {
		return this.lockRoot;
	}

	@Override
	public int hashCode() {
		return hash(this.lockScope, this.lockType, this.depth, this.owner, this.timeOut, this.lockToken, this.lockRoot);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof ActiveLock))
			return false;

		final ActiveLock that = (ActiveLock) other;

		return Arrays.equals(array(this.lockScope, this.lockType, this.depth, this.owner, this.timeOut, this.lockToken, this.lockRoot),
				array(that.lockScope, that.lockType, that.depth, that.owner, that.timeOut, that.lockToken, that.lockRoot));
	}

	@Override
	public String toString() {
		return Utilities.toString(this, this.lockScope, this.lockToken, this.depth, this.owner, this.timeOut, this.lockToken, this.lockRoot);
	}
}
