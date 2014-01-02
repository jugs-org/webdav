/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2014 The java.net WebDAV Project
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

import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static net.java.dev.webdav.util.Utilities.notNull;
import static net.java.dev.webdav.util.Utilities.sameOrEqual;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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

	public final LockScope getLockScope() {
		return this.lockScope;
	}

	public final LockType getLockType() {
		return this.lockType;
	}

	/**
	 * @deprecated Since 1.1.1. Use {@link #getDepth()} instead.
	 */
	@Deprecated
	public final Depth depth() {
		return this.depth;
	}

	/**
	 * @since 1.1.1
	 */
	public final Depth getDepth() {
		return this.depth;
	}

	public final Owner getOwner() {
		return this.owner;
	}

	public final TimeOut getTimeOut() {
		return this.timeOut;
	}

	public final LockToken getLockToken() {
		return this.lockToken;
	}

	public final LockRoot getLockRoot() {
		return this.lockRoot;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(this.lockScope, this.lockType, this.depth, this.owner, this.timeOut, this.lockToken, this.lockRoot);
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof ActiveLock))
			return false;

		final ActiveLock that = (ActiveLock) other;

		return this.lockScope == that.lockScope && this.lockType == that.lockType && this.depth == that.depth && sameOrEqual(this.owner, that.owner)
				&& sameOrEqual(this.timeOut, that.timeOut) && sameOrEqual(this.lockToken, that.lockToken) && sameOrEqual(this.lockRoot, that.lockRoot);
	}
}
