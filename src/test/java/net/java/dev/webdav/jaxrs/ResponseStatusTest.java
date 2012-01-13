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

package net.java.dev.webdav.jaxrs;

import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static net.java.dev.webdav.jaxrs.ResponseStatus.FAILED_DEPENDENCY;
import static net.java.dev.webdav.jaxrs.ResponseStatus.INSUFFICIENT_STORAGE;
import static net.java.dev.webdav.jaxrs.ResponseStatus.LOCKED;
import static net.java.dev.webdav.jaxrs.ResponseStatus.MULTI_STATUS;
import static net.java.dev.webdav.jaxrs.ResponseStatus.UNPROCESSABLE_ENTITY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response.Status.Family;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Tests WebDAV response statuses.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class ResponseStatusTest {
	@DataPoints
	public static final Object[][] dataPoints = new Object[][] {
		{ MULTI_STATUS, 207, "Multi-Status", SUCCESSFUL },
		{ UNPROCESSABLE_ENTITY, 422, "Unprocessable Entity", CLIENT_ERROR },
		{ LOCKED, 423, "Locked", CLIENT_ERROR },
		{ FAILED_DEPENDENCY, 424, "Failed Dependency", CLIENT_ERROR },
		{ INSUFFICIENT_STORAGE, 507, "Insufficient Storage", SERVER_ERROR }
	};

	@Theory
	public final void webDAVConstantsProduceCorrectly(final Object[] dataPoint) {
		final ResponseStatus actual = (ResponseStatus) dataPoint[0];
		final int expectedStatusCode = (Integer) dataPoint[1];
		final String expectedReasonPhrase = (String) dataPoint[2];
		final Family expectedFamily = (Family) dataPoint[3];

		assertThat(actual.getStatusCode(), is(expectedStatusCode));
		assertThat(actual.getReasonPhrase(), is(expectedReasonPhrase));
		assertThat(actual.getFamily(), is(expectedFamily));
	}
}
