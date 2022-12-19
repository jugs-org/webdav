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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.ResponseStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.StatusType;
import jakarta.xml.bind.JAXBException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link Status}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class StatusTest extends AbstractJaxbCoreFunctionality<Status> {

	@SuppressWarnings("deprecation")
	private static final Object[][] DATA_POINTS = {
			{ new Status(ResponseStatus.FAILED_DEPENDENCY), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 424 Failed Dependency</D:status>",
					"HTTP/1.1 424 Failed Dependency" },
			{ new Status(Response.Status.SERVICE_UNAVAILABLE), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 503 Service Unavailable</D:status>",
					"HTTP/1.1 503 Service Unavailable" },
			{ new Status((Response.StatusType) Response.Status.BAD_REQUEST), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 400 Bad Request</D:status>",
					"HTTP/1.1 400 Bad Request" } };

	@ParameterizedTest(name = "[{index}]")
	@ValueSource(ints = {0, 1, 2})
	void testMarshalling(int i) throws JAXBException {
		marshalling(DATA_POINTS[i]);
	}

	@ParameterizedTest(name = "[{index}]")
	@ValueSource(ints = {0, 1, 2})
	void testUnmarshalling(int i) throws JAXBException {
		unmarshalling(DATA_POINTS[i]);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final Status actual, final Status expected, final Object[] dataPoint) {
		assertThat(actual.getStatus(), is(dataPoint[2]));
		assertThat(expected.getStatus(), is(dataPoint[2]));
	}

	@Override
	protected Status getInstance() {
		return new Status((StatusType) ResponseStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	protected String getString() {
		return "Status[HTTP/1.1 422 Unprocessable Entity]";
	}

}
