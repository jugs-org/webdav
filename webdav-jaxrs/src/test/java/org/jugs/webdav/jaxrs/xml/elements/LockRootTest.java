/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2023 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link Include}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockRootTest extends AbstractJaxbCoreFunctionality<LockRoot> {

	@Test
	void constructorDoesNotAcceptNullURI() {
		assertThrows(NullArgumentException.class, () -> new LockRoot(null));
	}

	private static final HRef HREF = new HRef("http://localhost");
	private static final Object[] SINGLE_VALUE_CONSTRUCTOR = { new LockRoot(HREF),
			"<D:lockroot xmlns:D=\"DAV:\"><D:href>http://localhost</D:href></D:lockroot>", HREF };

	@Test
	void testMarshalling() throws JAXBException {
		marshalling(SINGLE_VALUE_CONSTRUCTOR);
	}

	@Test
	void testUnmarshalling() throws JAXBException {
		unmarshalling(SINGLE_VALUE_CONSTRUCTOR);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final LockRoot actual, final LockRoot expected, final Object[] dataPoint) {
		assertThat(actual.getHRef(), is(dataPoint[2]));
		assertThat(expected.getHRef(), is(dataPoint[2]));
	}

	@Override
	protected LockRoot getInstance() {
		return new LockRoot(HREF);
	}

	@Override
	protected String getString() {
		return "LockRoot[HRef[http://localhost]]";
	}

}
