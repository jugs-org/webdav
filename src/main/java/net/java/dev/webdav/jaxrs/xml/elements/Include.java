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
import static java.util.Collections.unmodifiableList;
import static net.java.dev.webdav.util.Utilities.notNull;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.util.Utilities;

/**
 * WebDAV include XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_include">Chapter 14.8 "include XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement
public final class Include {

	@XmlAnyElement(lax = true)
	private final List<Object> includes;

	@SuppressWarnings("unused")
	private Include() {
		this.includes = new LinkedList<Object>();
	}

	public Include(final Object... includes) {
		this.includes = asList(notNull(includes, "includes"));
	}

	public final List<Object> getIncludes() {
		return unmodifiableList(this.includes);
	}

	@Override
	public final int hashCode() {
		return this.includes.hashCode();
	}

	@Override
	public final boolean equals(final Object other) {
		if (other == this)
			return true;

		if (!(other instanceof Include))
			return false;

		final Include that = (Include) other;

		return this.includes.equals(that.includes);
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.includes);
	}
}
