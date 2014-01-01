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

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.elements.ActiveLock;

/**
 * WebDAV lockdiscovery Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_lockdiscovery">Chapter 15.8 "lockdiscovery Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "lockdiscovery")
public final class LockDiscovery {

	@XmlElement(name = "activelock")
	private LinkedList<ActiveLock> activeLocks;

	public LockDiscovery() {
		this.activeLocks = new LinkedList<ActiveLock>();
	}

	public LockDiscovery(final ActiveLock... activeLocks) {
		if (activeLocks == null)
			throw new NullArgumentException("lockEntries");

		this.activeLocks = new LinkedList<ActiveLock>(asList(activeLocks));
	}

	@SuppressWarnings("unchecked")
	public final List<ActiveLock> getActiveLocks() {
		return (List<ActiveLock>) this.activeLocks.clone();
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
}
