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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * WebDAV getetag Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getetag">Chapter 15.6 "getetag Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "getetag")
public final class GetETag {

	@XmlValue
	private final String entityTag;

	/**
	 * Creates an empty (thus <em>invalid</em>) instance. Use <em>only</em> to list property name within response to &lt;propname/&gt; request. Not to be used
	 * for creation of valid instances of this property; use {@link #GetETag(String)} instead.
	 */
	public GetETag() {
		this.entityTag = "";
	}

	public GetETag(final String entityTag) {
		if (entityTag == null)
			throw new NullArgumentException("entityTag");

		this.entityTag = entityTag;
	}

	public final String getEntityTag() {
		return this.entityTag;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof GetETag))
			return false;

		final GetETag that = (GetETag) other;

		return this.entityTag.equals(that.entityTag);
	}

	@Override
	public final int hashCode() {
		return this.entityTag.hashCode();
	}
}
