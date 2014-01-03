/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2014 The java.net WebDAV Project
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

package net.java.dev.webdav.jaxrs.xml.elements;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import net.java.dev.webdav.jaxrs.ResponseStatus;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;

/**
 * Unit test for {@link Status}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class StatusTest extends AbstractJaxbCoreFunctionality<Status> {
	@SuppressWarnings("deprecation")
	@DataPoints
	public static final Object[][] DATA_POINTS = {
			{ new Status(ResponseStatus.FAILED_DEPENDENCY), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 424 Failed Dependency</D:status>",
					"HTTP/1.1 424 Failed Dependency" },
			{ new Status(Response.Status.SERVICE_UNAVAILABLE), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 503 Service Unavailable</D:status>",
					"HTTP/1.1 503 Service Unavailable" },
			{ new Status((Response.StatusType) Response.Status.BAD_REQUEST), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 400 Bad Request</D:status>",
					"HTTP/1.1 400 Bad Request" } };

	@Override
	protected void assertThatGettersProvideExpectedValues(final Status actual, final Status expected, final Object[] dataPoint) {
		assertThat(actual.getStatus(), is(dataPoint[2]));
		assertThat(expected.getStatus(), is(dataPoint[2]));
	}

	@SuppressWarnings("deprecation")
	@Test(expected = NullPointerException.class)
	public final void responseStatusConstructorDoesNotAcceptNull() {
		new Status((ResponseStatus) null);
	}

	@SuppressWarnings("deprecation")
	@Test(expected = NullPointerException.class)
	public final void statusConstructorDoesNotAcceptNull() {
		new Status((Response.Status) null);
	}

	@Test(expected = NullPointerException.class)
	public final void StatusTypeConstructorDoesNotAcceptNull() {
		new Status((Response.StatusType) null);
	}

	@Override
	protected final Status getInstance() {
		return new Status((StatusType) ResponseStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	protected final String getString() {
		return "Status[HTTP/1.1 422 Unprocessable Entity]";
	}
}
