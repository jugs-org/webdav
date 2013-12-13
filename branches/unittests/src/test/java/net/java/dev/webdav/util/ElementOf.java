/*
 * Copyright 2012 Markus KARG
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

package net.java.dev.webdav.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * This hamcrest matcher checks whether a class is known to a particular JAXB context.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ElementOf extends TypeSafeMatcher<Class<?>> {
	private final JAXBContext context;

	private final JAXBIntrospector introspector;

	public ElementOf(final JAXBContext context) {
		this.context = context;
		this.introspector = context.createJAXBIntrospector();
	}

	public static final ElementOf elementOf(final JAXBContext context) {
		return new ElementOf(context);
	}

	@Override
	public final void describeTo(final Description description) {
		description.appendText("element of this JAXB context: " + this.context.toString());
	}

	@Override
	public final boolean matchesSafely(final Class<?> cls) {
		return this.introspector.isElement(Utilities.buildInstanceOf(cls));
	}
}
