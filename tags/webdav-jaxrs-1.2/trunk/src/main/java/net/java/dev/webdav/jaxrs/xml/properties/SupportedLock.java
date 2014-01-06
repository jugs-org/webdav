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

package net.java.dev.webdav.jaxrs.xml.properties;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableList;
import static net.java.dev.webdav.util.Utilities.notNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.java.dev.webdav.jaxrs.ConstantsAdapter;
import net.java.dev.webdav.jaxrs.xml.elements.LockEntry;
import net.java.dev.webdav.util.Utilities;

/**
 * WebDAV supportedlock Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_supportedlock">Chapter 15.10 "supportedlock Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(SupportedLock.Adapter.class)
@XmlRootElement(name = "supportedlock")
public final class SupportedLock {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final SupportedLock SUPPORTEDLOCK = new SupportedLock();

	@XmlElement(name = "lockentry")
	private final List<LockEntry> lockEntries;

	/**
	 * @deprecated Since 1.2. Use {@link #SUPPORTEDLOCK} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
	public SupportedLock() {
		this.lockEntries = new LinkedList<LockEntry>();
	}

	public SupportedLock(final LockEntry... lockEntries) {
		this.lockEntries = asList(notNull(lockEntries, "lockEntries"));
	}

	public final List<LockEntry> getLockEntries() {
		return unmodifiableList(this.lockEntries);
	}

	@Override
	public final int hashCode() {
		return this.lockEntries.hashCode();
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof SupportedLock))
			return false;

		final SupportedLock that = (SupportedLock) other;

		return this.lockEntries.equals(that.lockEntries);
	}

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<SupportedLock> {
		@Override
		protected final Collection<SupportedLock> getConstants() {
			return singleton(SUPPORTEDLOCK);
		}
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.lockEntries);
	}
}
