/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
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

package org.jugs.webdav.util;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Unit test for {@link ElementOf}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ElementOfTest {
	private static JAXBContext context;

	@BeforeAll
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
