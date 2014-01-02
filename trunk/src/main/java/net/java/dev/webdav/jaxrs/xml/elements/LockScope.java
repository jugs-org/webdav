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

import static java.util.Arrays.asList;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static net.java.dev.webdav.util.Utilities.sameOrEqual;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.java.dev.webdav.jaxrs.ConstantsAdapter;

/**
 * WebDAV lockscope XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_lockscope">Chapter 14.13 "lockscope XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = { "exclusive", "shared" })
@XmlJavaTypeAdapter(LockScope.Adapter.class)
@XmlRootElement(name = "lockscope")
public final class LockScope {

	public static final LockScope SHARED = new LockScope(Shared.SHARED, null);

	public static final LockScope EXCLUSIVE = new LockScope(null, Exclusive.EXCLUSIVE);

	private final Shared shared;

	private final Exclusive exclusive;

	// Singleton
	private LockScope() {
		this.shared = null;
		this.exclusive = null;
	}

	// Enum
	private LockScope(final Shared shared, final Exclusive exclusive) {
		this.shared = shared;
		this.exclusive = exclusive;
	}

	@Override
	public final int hashCode() {
		return (this.shared == null ? -1 : this.shared.hashCode()) ^ (this.exclusive == null ? -1 : this.exclusive.hashCode());
	}

	@Override
	public final boolean equals(final Object other) {
		if (other == this)
			return true;

		if (!(other instanceof LockScope))
			return false;

		final LockScope that = (LockScope) other;

		return sameOrEqual(this.exclusive, that.exclusive) && sameOrEqual(this.shared, that.shared);
	}

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<LockScope> {
		@Override
		protected final Collection<LockScope> getConstants() {
			return asList(SHARED, EXCLUSIVE);
		}
	}
}
