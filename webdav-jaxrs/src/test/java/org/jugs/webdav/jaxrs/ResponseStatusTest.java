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

package org.jugs.webdav.jaxrs;

import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static org.jugs.webdav.jaxrs.ResponseStatus.FAILED_DEPENDENCY;
import static org.jugs.webdav.jaxrs.ResponseStatus.INSUFFICIENT_STORAGE;
import static org.jugs.webdav.jaxrs.ResponseStatus.LOCKED;
import static org.jugs.webdav.jaxrs.ResponseStatus.MULTI_STATUS;
import static org.jugs.webdav.jaxrs.ResponseStatus.UNPROCESSABLE_ENTITY;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.ws.rs.core.Response.Status.Family;

import org.jugs.webdav.jaxrs.ResponseStatus;
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
	public static final Object[][] DATA_POINTS = new Object[][] { { MULTI_STATUS, 207, "Multi-Status", SUCCESSFUL },
			{ UNPROCESSABLE_ENTITY, 422, "Unprocessable Entity", CLIENT_ERROR }, { LOCKED, 423, "Locked", CLIENT_ERROR },
			{ FAILED_DEPENDENCY, 424, "Failed Dependency", CLIENT_ERROR }, { INSUFFICIENT_STORAGE, 507, "Insufficient Storage", SERVER_ERROR } };

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
