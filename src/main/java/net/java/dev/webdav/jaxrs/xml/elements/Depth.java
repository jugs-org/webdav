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

import static net.java.dev.webdav.jaxrs.Headers.DEPTH_0;
import static net.java.dev.webdav.jaxrs.Headers.DEPTH_1;
import static net.java.dev.webdav.jaxrs.Headers.DEPTH_INFINITY;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.Headers;

/**
 * WebDAV depth XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_depth">Chapter 14.4 "depth XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement
public enum Depth {
	@XmlEnumValue(DEPTH_0)
	ZERO,

	@XmlEnumValue(DEPTH_1)
	ONE,

	@XmlEnumValue(DEPTH_INFINITY)
	INFINITY;

	/**
	 * Factory method creating {@link Depth} instances from {@code String} value representations (e. g. as used in HTTP {@link Headers#DEPTH} header).
	 * Guarantees that enums are returned for {@code "0"}, {@code "1"} and {@code "infinity"} strings, hence allowing to compare using {@code ==} comparison.
	 * <p>
	 * Example:<br/>
	 * <code>
	 * Depth d = Depth.fromString("infinity");<br/>
	 * if (d == Depth.INFINITY) { ... }<br/>
	 * </code>
	 * </p>
	 * 
	 * @param depth
	 *            Either {@code "0"}, {@code "1"} or {@code "infinity"}.
	 * @return An enum of {@link Depth} produced from the provided string. Instance is guaranteed to be either {@link #ZERO}, {@link #ONE} or {@link #INFINITY}.
	 * @throws IllegalArgumentException
	 *             in case an invalid string value is passed in.
	 * @since 2.0
	 */
	public static final Depth fromString(final String depth) {
		switch (depth) {
		case DEPTH_0:
			return ZERO;
		case DEPTH_1:
			return ONE;
		case DEPTH_INFINITY:
			return INFINITY;
		default:
			throw new IllegalArgumentException();
		}
	}
}
