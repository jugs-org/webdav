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

package net.java.dev.webdav.jaxrs.xml.properties;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * WebDAV displayname Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_displayname">Chapter 15.2 "displayname Property" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "displayname")
public final class DisplayName {

	@XmlValue
	private final String name;

	/**
	 * Creates an empty (thus <em>invalid</em>) instance. Use <em>only</em> to list property name within response to &lt;propname/&gt; request. Not to be used for creation of valid instances of this property; use {@link #DisplayName(String)} instead.
	 */
	public DisplayName() {
		this.name = "";
	}

	public DisplayName(final String name) {
		if (name == null)
			throw new NullArgumentException("name");

		this.name = name;
	}

	public final String getName() {
		return this.name;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof DisplayName))
			return false;

		final DisplayName that = (DisplayName) other;

		return this.name.equals(that.name);
	}

	@Override
	public final int hashCode() {
		return this.name.hashCode();
	}
}
