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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * WebDAV shared XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_shared">Chapter 14.27 "shared XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement
@XmlType(factoryMethod = "create")
public final class Shared {

	/**
	 * @since 1.1.1
	 */
	public static final Shared SINGLETON = new Shared();

	// Singleton
	private Shared() {
		// Contains no members.
	}

	@SuppressWarnings("unused")
	private static final Shared create() {
		return SINGLETON;
	}

	@Override
	public final boolean equals(final Object object) {
		return object instanceof Shared;
	}

	@Override
	public final int hashCode() {
		return 0;
	}
}
