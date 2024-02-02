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
import org.jugs.webdav.jaxrs.NullArgumentException;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLanguage;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBException;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link Error}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ErrorTest extends AbstractJaxbCoreFunctionality<Error> {

	@Test
	public void constructorDoesNotAcceptNullError() {
		assertThrows(NullArgumentException.class, () -> new Error(null));
	}

	private static final Object FIRST_ERROR = new Prop();
	private static final Object SECOND_ERROR = GetContentLanguage.GETCONTENTLANGUAGE;
	private static final Object[] ONE_ERROR = { new Error(FIRST_ERROR), "<D:error xmlns:D=\"DAV:\"><D:prop/></D:error>", asList(FIRST_ERROR) };
	private static final Object[] TWO_ERRORS = { new Error(FIRST_ERROR, SECOND_ERROR), "<D:error xmlns:D=\"DAV:\"><D:prop/><D:getcontentlanguage/></D:error>",
			asList(FIRST_ERROR, SECOND_ERROR) };

	@Test
	void marshallingOneError() throws JAXBException {
		marshalling(ONE_ERROR);
	}

	@Test
	void unmarshallingOneError() throws JAXBException {
		unmarshalling(ONE_ERROR);
	}

	@Test
	void marshallingTwoErrors() throws JAXBException {
		marshalling(TWO_ERRORS);
	}

	@Test
	void unmarshallingTwoErrors() throws JAXBException {
		unmarshalling(TWO_ERRORS);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final Error actual, final Error expected, final Object[] dataPoint) {
		assertThat(actual.getErrors(), is(dataPoint[2]));
		assertThat(expected.getErrors(), is(dataPoint[2]));
	}

	@Override
	protected Error getInstance() {
		return new Error("ERROR");
	}

	@Override
	protected String getString() {
		return "Error[[ERROR]]";
	}

}
