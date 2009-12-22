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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Versioning Extensions to WebDAV creator-displayname Property.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc3253.html#PROPERTY_creator-displayname">Chapter 3.1.2 "DAV:creator-displayname" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
 */
@XmlRootElement(name = "creator-displayname")
public final class CreatorDisplayName {

	@XmlValue
	private String creatorDisplayName;

	public CreatorDisplayName() {
		// Keeping defaults by intention.
	}

	public CreatorDisplayName(final String creatorDisplayName) {
		this.creatorDisplayName = creatorDisplayName;
	}

	public final String getComment() {
		return this.creatorDisplayName;
	}

}
