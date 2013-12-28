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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * WebDAV allprop XML Element.
 * 
 * <p>
 * This is a singleton. All instances are absolutely identical, hence can be compared using {@code ==} and share one unique hash code. Use {@link #ALL_PROP}
 * always.
 * </p>
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_allprop">Chapter 14.2 "allprop XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "allprop")
@XmlType(factoryMethod = "createSingleton")
public final class AllProp {
	/**
	 * Singleton instance, providing improved performance and the ability to compare by <em>same</em> instance.
	 */
	public static final AllProp ALL_PROP = new AllProp();

	/**
	 * Singleton factory to be used solely by JAXB.
	 */
	@SuppressWarnings("unused")
	private static final AllProp createSingleton() {
		return ALL_PROP;
	}

	/**
	 * @deprecated Since 1.2. Use {@link #ALL_PROP} instead to obtain a singleton. In future releases this will have {@code private} visibility.
	 */
	@Deprecated
	public AllProp() {
		// Has no members.
	}

	@Override
	public final boolean equals(final Object other) {
		return other instanceof AllProp;
	}

	@Override
	public final int hashCode() {
		return 0;
	}
}
