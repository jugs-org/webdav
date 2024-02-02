/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2024 Java User Group Stuttgart
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
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link ResponseDescription}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ResponseDescriptionTest extends AbstractJaxbCoreFunctionality<ResponseDescription> {

	private static final Object[] DATA_POINT = { new ResponseDescription("some content"),
			"<D:responsedescription xmlns:D=\"DAV:\">some content</D:responsedescription>", "some content" };

	@Test
	void testMarshalling() throws JAXBException {
		marshalling(DATA_POINT);
	}

	@Test
	void testUnmarshalling() throws JAXBException {
		unmarshalling(DATA_POINT);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final ResponseDescription actual, final ResponseDescription expected, final Object[] dataPoint) {
		assertThat(actual.getContent(), is(dataPoint[2]));
		assertThat(expected.getContent(), is(dataPoint[2]));
	}

	@Override
	protected ResponseDescription getInstance() {
		return new ResponseDescription("some content");
	}

	@Override
	protected String getString() {
		return "ResponseDescription[some content]";
	}

}
