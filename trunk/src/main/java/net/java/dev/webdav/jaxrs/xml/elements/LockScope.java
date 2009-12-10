/*
 * Copyright 2008, 2009 Markus KARG
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

package net.java.dev.webdav.jaxrs.xml.elements;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * WebDAV lockscope XML Element.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_lockscope">Chapter 14.13 "lockscope XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = { "exclusive", "shared" })
@XmlRootElement(name = "lockscope")
public final class LockScope {

	/*
	 * TODO LockScope should not be class but enum, but how to tell
	 * 
	 * @XmlEnumValue that the value is not #PCDATA but ELEMENT?
	 */

	public static final LockScope SHARED = new LockScope(new Shared(), null);

	public static final LockScope EXCLUSIVE = new LockScope(null, new Exclusive());

	@SuppressWarnings("unused")
	private Shared shared;

	@SuppressWarnings("unused")
	private Exclusive exclusive;

	private LockScope() {
		// For unmarshalling only.
	}

	private LockScope(final Shared shared, final Exclusive exclusive) {
		this.shared = shared;
		this.exclusive = exclusive;
	}

}
