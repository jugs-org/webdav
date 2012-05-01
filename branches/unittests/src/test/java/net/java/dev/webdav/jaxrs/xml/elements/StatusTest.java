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

package net.java.dev.webdav.jaxrs.xml.elements;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;

import net.java.dev.webdav.jaxrs.ResponseStatus;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Unit test for {@link Status}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class StatusTest {
	@SuppressWarnings("deprecation")
	@DataPoints
	public static final Object[][] DATA_POINTS = {
		{ new Status(ResponseStatus.FAILED_DEPENDENCY), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 424 Failed Dependency</D:status>", "HTTP/1.1 424 Failed Dependency" },
		{ new Status(Response.Status.SERVICE_UNAVAILABLE), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 503 Service Unavailable</D:status>", "HTTP/1.1 503 Service Unavailable" },
		{ new Status((Response.StatusType) Response.Status.BAD_REQUEST), "<D:status xmlns:D=\"DAV:\">HTTP/1.1 400 Bad Request</D:status>", "HTTP/1.1 400 Bad Request" }
	};

	@Theory
	public final void marshalling(final Object[] dataPoint) {
		final Writer writer = new StringWriter();
		JAXB.marshal(dataPoint[0], writer);
		final String actual = writer.toString();
		final String expected = (String) dataPoint[1];
		assertThat(the(actual), isEquivalentTo(the(expected)));
	}

	@Theory
	public final void unmarshalling(final Object[] dataPoint) {
		final Reader reader = new StringReader((String) dataPoint[1]);
		final Status actual = JAXB.unmarshal(reader, Status.class);
		final Status expected = (Status) dataPoint[0];
		assertThat(actual, is(expected));
		assertThat(actual.getStatus(), is(dataPoint[2]));
	}
}