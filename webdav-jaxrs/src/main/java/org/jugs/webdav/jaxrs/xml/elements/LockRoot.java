/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
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

import static org.jugs.webdav.util.Utilities.notNull;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.jugs.webdav.util.Utilities;

/**
 * WebDAV lockroot XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_lockroot">Chapter 14.12 "lockroot XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "lockroot")
public final class LockRoot {

	@XmlElement(name = "href")
	private final HRef hRef;

	@SuppressWarnings("unused")
	private LockRoot() {
		this.hRef = null;
	}

	public LockRoot(final HRef hRef) {
		this.hRef = notNull(hRef, "hRef");
	}

	public final HRef getHRef() {
		return this.hRef;
	}

	@Override
	public final int hashCode() {
		return this.hRef.hashCode();
	}

	@Override
	public final boolean equals(final Object other) {
		if (other == this)
			return true;

		if (!(other instanceof LockRoot))
			return false;

		final LockRoot that = (LockRoot) other;

		return this.hRef.equals(that.hRef);
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.hRef);
	}
}
