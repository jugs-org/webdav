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

import static java.util.Collections.unmodifiableList;
import static org.jugs.webdav.util.Utilities.append;
import static org.jugs.webdav.util.Utilities.notNull;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.jugs.webdav.util.Utilities;

/**
 * WebDAV error XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_error">Chapter 14.5 "error XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement
public final class Error {

	@XmlAnyElement(lax = true)
	public final List<Object> errors;

	@SuppressWarnings("unused")
	private Error() {
		this.errors = new LinkedList<Object>();
	}

	public Error(final Object error, final Object... errors) {
		this.errors = append(notNull(error, "error"), errors);
	}

	public final List<Object> getErrors() {
		return unmodifiableList(this.errors);
	}

	@Override
	public final int hashCode() {
		return this.errors.hashCode();
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof Error))
			return false;

		final Error that = (Error) other;

		return this.errors.equals(that.errors);
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.errors);
	}
}
