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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for {@link ElementOf}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ElementOfTest {
	private static JAXBContext context;

	@BeforeClass
	public static final void setUp() throws JAXBException {
		context = JAXBContext.newInstance(ContainedMockElement.class);
	}

	@Test
	public final void isHamcrestMatcher() {
		assertThat(new ElementOf(context), is(instanceOf(Matcher.class)));
	}

	@Test
	public final void factoryClassProducesElementOfMatcher() {
		assertThat(ElementOf.elementOf(context), is(instanceOf(ElementOf.class)));
	}

	@XmlRootElement
	private static final class ContainedMockElement {
		// Intentionally left blank.
	}

	@XmlRootElement
	private static final class NotContainedMockElement {
		// Intentionally left blank.
	}

	@Test
	public final void matchesAndNotMatchesAsExcepted() {
		final Matcher<Class<?>> elementOf = ElementOf.elementOf(context);
		assertThat(elementOf.matches(ContainedMockElement.class), is(true));
		assertThat(elementOf.matches(NotContainedMockElement.class), is(false));
	}

	@Test
	public final void describesComprehensibly() {
		final Description description = new StringDescription();
		ElementOf.elementOf(context).describeTo(description);
		assertThat(description.toString(), startsWith("element of this JAXB context:"));
	}
}
