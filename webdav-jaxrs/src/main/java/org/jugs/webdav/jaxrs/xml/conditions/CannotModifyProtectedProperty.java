/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2022 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs.xml.conditions;

import org.jugs.webdav.util.Utilities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * WebDAV cannot-modify-protected-property Precondition XML Element.
 * 
 * <p>
 * This is a singleton. All instances are absolutely identical, hence can be compared using {@code ==} and share one unique hash code. Use
 * {@link #CANNOT_MODIFY_PROTECTED_PROPERTY} always.
 * </p>
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#precondition.postcondition.xml.elements">Chapter 16 "Precondition/Postcondition XML Elements" of RFC
 *      4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "cannot-modify-protected-property")
@XmlType(factoryMethod = "createSingleton")
public final class CannotModifyProtectedProperty {
	/**
	 * Singleton instance, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final CannotModifyProtectedProperty CANNOT_MODIFY_PROTECTED_PROPERTY = new CannotModifyProtectedProperty();

	@SuppressWarnings("unused")
	private static CannotModifyProtectedProperty createSingleton() {
		return CANNOT_MODIFY_PROTECTED_PROPERTY;
	}

	private CannotModifyProtectedProperty() {
		// For unmarshalling only.
	}

	@Override
	public boolean equals(final Object object) {
		return object instanceof CannotModifyProtectedProperty;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public String toString() {
		return Utilities.toString(this);
	}

}
