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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Versioning Extensions to WebDAV report Property.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/deltav/protocol/rfc3253.html#PROPERTY_supported-report-set">Chapter 3.1.5 "DAV:supported-report-set (protected)" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
 */
@XmlRootElement
public final class Report {

	@XmlMixed
	@XmlAnyElement(lax = true)
	private LinkedList<Object> any;

	public Report() {
		// Has no members.
	}

	public Report(final Object... any) {
		this.any = new LinkedList<Object>(Arrays.asList(any));
	}

	@SuppressWarnings("unchecked")
	public final List<Object> getAny() {
		return (List<Object>) this.any.clone();
	}

}
