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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.util.Utilities;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBException;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link MultiStatus}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class MultiStatusTest extends AbstractJaxbCoreFunctionality<MultiStatus> {
	private static final Response RESPONSE = Utilities.buildInstanceOf(Response.class);
	private static final ResponseDescription RESPONSE_DESCRIPTION = new ResponseDescription("X");

	private static final Object[] SINGLE_RESPONSE_ONLY = { new MultiStatus(RESPONSE), "<D:multistatus xmlns:D=\"DAV:\"><D:response/></D:multistatus>",
			asList(RESPONSE), null };
	private static final Object[] SINGLE_RESPONSE_WITH_RESPONSE_DESCRIPTION = { new MultiStatus(RESPONSE_DESCRIPTION, RESPONSE),
			"<D:multistatus xmlns:D=\"DAV:\"><D:response/><D:responsedescription>X</D:responsedescription></D:multistatus>", asList(RESPONSE),
			RESPONSE_DESCRIPTION };
	private static final Object[] MULTI_RESPONSE_WITH_RESPONSE_DESCRIPTION = { new MultiStatus(RESPONSE_DESCRIPTION, RESPONSE, RESPONSE),
			"<D:multistatus xmlns:D=\"DAV:\"><D:response/><D:response/><D:responsedescription>X</D:responsedescription></D:multistatus>",
			asList(RESPONSE, RESPONSE), RESPONSE_DESCRIPTION };
	private static final Object[] RESPONSE_DESCRIPTION_ONLY = { new MultiStatus(RESPONSE_DESCRIPTION),
			"<D:multistatus xmlns:D=\"DAV:\"><D:responsedescription>X</D:responsedescription></D:multistatus>", EMPTY_LIST, RESPONSE_DESCRIPTION };

	@Test
	void marshallingSingleResponseOnly() throws JAXBException {
		marshalling(SINGLE_RESPONSE_ONLY);
	}

	@Test
	void unmarshallingSingleResponseOnly() throws JAXBException {
		unmarshalling(SINGLE_RESPONSE_ONLY);
	}

	@Test
	void marshallingSingleResponse() throws JAXBException {
		marshalling(SINGLE_RESPONSE_WITH_RESPONSE_DESCRIPTION);
	}

	@Test
	void unmarshallingSingleResponse() throws JAXBException {
		unmarshalling(SINGLE_RESPONSE_WITH_RESPONSE_DESCRIPTION);
	}

	@Test
	void marshallingMultiResponse() throws JAXBException {
		marshalling(MULTI_RESPONSE_WITH_RESPONSE_DESCRIPTION);
	}

	@Test
	void unmarshallingMultiResponse() throws JAXBException {
		unmarshalling(MULTI_RESPONSE_WITH_RESPONSE_DESCRIPTION);
	}

	@Test
	void marshallingResponseDescriptionOnly() throws JAXBException {
		marshalling(RESPONSE_DESCRIPTION_ONLY);
	}

	@Test
	void unmarshallingResponseDescriptionOnly() throws JAXBException {
		unmarshalling(RESPONSE_DESCRIPTION_ONLY);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final MultiStatus actual, final MultiStatus expected, final Object[] dataPoint) {
		assertThat(actual.getResponses(), is(dataPoint[2]));
		assertThat(actual.getResponseDescription(), is(dataPoint[3]));
		assertThat(expected.getResponses(), is(dataPoint[2]));
		assertThat(expected.getResponseDescription(), is(dataPoint[3]));
	}

	@Override
	protected MultiStatus getInstance() {
		return new MultiStatus(RESPONSE);
	}

	@Override
	protected String getString() {
		return "MultiStatus[[Response[null, [], null, null, null]], null]";
	}

}
