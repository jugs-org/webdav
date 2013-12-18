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

import static java.util.Arrays.asList;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * WebDAV owner XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_owner">Chapter 14.17 "owner XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement
public final class Owner {

	@XmlMixed
	@XmlAnyElement(lax = true)
	private LinkedList<Object> any;

	@SuppressWarnings("unused")
	private Owner() {
		this.any = new LinkedList<Object>();
	}

	public Owner(final Object... any) {
		if (any == null)
			throw new NullArgumentException("any");

		this.any = new LinkedList<Object>(asList(any));
	}

	@SuppressWarnings("unchecked")
	public final List<Object> getAny() {
		return (List<Object>) this.any.clone();
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof Owner))
			return false;

		final Owner that = (Owner) other;

		return this.any.equals(that.any);
	}
}
