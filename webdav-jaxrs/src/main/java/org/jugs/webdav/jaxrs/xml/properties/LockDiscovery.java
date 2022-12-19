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

package org.jugs.webdav.jaxrs.xml.properties;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableList;
import static org.jugs.webdav.util.Utilities.notNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jugs.webdav.jaxrs.ConstantsAdapter;
import org.jugs.webdav.jaxrs.xml.elements.ActiveLock;
import org.jugs.webdav.util.Utilities;

/**
 * WebDAV lockdiscovery Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_lockdiscovery">Chapter 15.8 "lockdiscovery Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(LockDiscovery.Adapter.class)
@XmlRootElement(name = "lockdiscovery")
public final class LockDiscovery {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final LockDiscovery LOCKDISCOVERY = new LockDiscovery();

	@XmlElement(name = "activelock")
	private final List<ActiveLock> activeLocks;

	/**
	 * @deprecated Since 1.2. Use {@link #LOCKDISCOVERY} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
	public LockDiscovery() {
		this.activeLocks = new LinkedList<ActiveLock>();
	}

	public LockDiscovery(final ActiveLock... activeLocks) {
		this.activeLocks = asList(notNull(activeLocks, "activeLocks"));
	}

	public final List<ActiveLock> getActiveLocks() {
		return unmodifiableList(this.activeLocks);
	}

	@Override
	public final int hashCode() {
		return this.activeLocks.hashCode();
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof LockDiscovery))
			return false;

		final LockDiscovery that = (LockDiscovery) other;

		return this.activeLocks.equals(that.activeLocks);
	}

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<LockDiscovery> {
		@Override
		protected final Collection<LockDiscovery> getConstants() {
			return singleton(LOCKDISCOVERY);
		}
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.activeLocks);
	}
}
