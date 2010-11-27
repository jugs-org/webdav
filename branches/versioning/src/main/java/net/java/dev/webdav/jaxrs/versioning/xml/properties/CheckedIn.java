/*
 * Copyright 2009 Markus KARG
 *
 * This file is part of webdav-jaxrs.
 *
 * webdav-jaxrs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * webdav-jaxrs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with webdav-jaxrs.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.java.dev.webdav.jaxrs.versioning.xml.properties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.elements.HRef;

/**
 * Versioning Extensions to WebDAV <code>checked-in</code> Property.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/deltav/protocol/rfc3253.html#PROPERTY_checked-in">Chapter 3.2.1 "DAV:checked-in (protected)" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
 */
@XmlRootElement(name = "checked-in")
public final class CheckedIn {

	@XmlElement(name = "href")
	private HRef hRef;

	/**
	 * Creates an empty (thus <em>invalid</em>) instance. Use <em>only</em> to
	 * list property name within response to &lt;propname/&gt; request. Not to
	 * be used for creation of valid instances of this property; use
	 * {@link #CheckedIn(HRef)} instead.
	 */
	public CheckedIn() {
		// Keeping defaults by intention.
	}

	public CheckedIn(final HRef hRef) {
		if (hRef == null)
			throw new NullArgumentException("hRef");			
		
		this.hRef = hRef;
	}

	public final HRef getHRef() {
		return this.hRef;
	}

}
