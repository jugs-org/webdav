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

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.jugs.webdav.util.Utilities;

/**
 * WebDAV write XML Element.
 * 
 * <p>
 * This is a singleton. All instances are absolutely identical, hence can be compared using {@code ==} and share one unique hash code. Use {@link #WRITE}
 * always.
 * </p>
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_write">Chapter 14.30 "write XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement
@XmlType(factoryMethod = "createSingleton")
public final class Write {
	/**
	 * Singleton instance, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final Write WRITE = new Write();

	/**
	 * @since 1.1.1
	 * @deprecated Since 1.2. Use {@link #WRITE} instead to obtain a singleton. In future releases this will be removed.
	 */
	@Deprecated
	public static final Write SINGLETON = WRITE;

	@SuppressWarnings("unused")
	private static final Write createSingleton() {
		return WRITE;
	}

	private Write() {
		// For unmarshalling only.
	}

	@Override
	public final boolean equals(final Object object) {
		return object instanceof Write;
	}

	@Override
	public final int hashCode() {
		return 1;
	}

	@Override
	public final String toString() {
		return Utilities.toString(this);
	}
}
