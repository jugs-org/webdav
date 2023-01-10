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

import jakarta.ws.rs.core.Response.StatusType;
import jakarta.xml.bind.JAXBException;
import java.util.LinkedList;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jugs.webdav.jaxrs.ResponseStatus.MULTI_STATUS;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link Response}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ResponseTest extends AbstractJaxbCoreFunctionality<Response> {

	private static final HRef HREF = new HRef("http://localhost");
	private static final Status STATUS = new Status((StatusType) MULTI_STATUS);
	private static final org.jugs.webdav.jaxrs.xml.elements.Error ERROR = new Error(new Prop());
	private static final ResponseDescription RESPONSE_DESCRIPTION = new ResponseDescription("X");
	private static final Location LOCATION = new Location(HREF);
	private static final PropStat PROP_STAT = new PropStat(new Prop(), STATUS);

	@Test
	void constructorShouldNotAcceptNullHRef() {
		assertThrows(NullArgumentException.class, () -> new Response(STATUS, ERROR, RESPONSE_DESCRIPTION, LOCATION, null));
	}

	@Test
	void constructorShouldNotAcceptNullStatus() {
		assertThrows(NullArgumentException.class, () -> new Response(null, ERROR, RESPONSE_DESCRIPTION, LOCATION, HREF));
	}

	@Test
	void constructorShouldNotAcceptNullPropStats() {
		assertThrows(NullArgumentException.class, () -> new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, (PropStat) null));
	}

	@SuppressWarnings("deprecation")
	@Test
	void constructorShouldNotAcceptNullPropStatCollection() {
		assertThrows(NullArgumentException.class, () -> new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, null));
	}

	@SuppressWarnings("deprecation")
	@Test
	void constructorShouldNotAcceptEmptyPropStatCollection() {
		assertThrows(NullArgumentException.class, () -> new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, new LinkedList<>()));
	}

	private static final Object[] STATUS_VARIANT = {
			new Response(STATUS, ERROR, RESPONSE_DESCRIPTION, LOCATION, HREF),
			"<D:response xmlns:D=\"DAV:\"><D:href>http://localhost</D:href><D:status>HTTP/1.1 207 Multi-Status</D:status><D:error><D:prop/></D:error><D:responsedescription>X</D:responsedescription><D:location><D:href>http://localhost</D:href></D:location></D:response>",
			asList(HREF), STATUS, EMPTY_LIST, ERROR, RESPONSE_DESCRIPTION, LOCATION };

	private static final Object[] PROPSTATS_VARIANT = {
			new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, PROP_STAT, PROP_STAT, PROP_STAT),
			"<D:response xmlns:D=\"DAV:\"><D:href>http://localhost</D:href><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:error><D:prop/></D:error><D:responsedescription>X</D:responsedescription><D:location><D:href>http://localhost</D:href></D:location></D:response>",
			asList(HREF), null, asList(PROP_STAT, PROP_STAT, PROP_STAT), ERROR, RESPONSE_DESCRIPTION, LOCATION };

	@SuppressWarnings("deprecation")
	private static final Object[] DEPRECATED_PROPSTATS_VARIANT = {
			new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, asList(PROP_STAT, PROP_STAT, PROP_STAT)),
			"<D:response xmlns:D=\"DAV:\"><D:href>http://localhost</D:href><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:error><D:prop/></D:error><D:responsedescription>X</D:responsedescription><D:location><D:href>http://localhost</D:href></D:location></D:response>",
			asList(HREF), null, asList(PROP_STAT, PROP_STAT, PROP_STAT), ERROR, RESPONSE_DESCRIPTION, LOCATION };

	@Test
	void marshallingStatus() throws JAXBException {
		marshalling(STATUS_VARIANT);
	}

	@Test
	void unmarshallingStatus() throws JAXBException {
		unmarshalling(STATUS_VARIANT);
	}

	@Test
	void marshallingPropstats() throws JAXBException {
		marshalling(PROPSTATS_VARIANT);
	}

	@Test
	void unmarshallingPropstats() throws JAXBException {
		unmarshalling(PROPSTATS_VARIANT);
	}

	@Test
	void marshallingDeprecatedPropstats() throws JAXBException {
		marshalling(DEPRECATED_PROPSTATS_VARIANT);
	}

	@Test
	void unmarshallingDeprecatedPropstats() throws JAXBException {
		unmarshalling(DEPRECATED_PROPSTATS_VARIANT);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final Response actual, final Response expected, final Object[] dataPoint) {
		assertThat(actual.getHRefs(), is(dataPoint[2]));
		assertThat(actual.getStatus(), is(dataPoint[3]));
		assertThat(actual.getPropStats(), is(dataPoint[4]));
		assertThat(expected.getError(), is(dataPoint[5]));
		assertThat(expected.getResponseDescription(), is(dataPoint[6]));
		assertThat(expected.getLocation(), is(dataPoint[7]));
		assertThat(expected.getHRefs(), is(dataPoint[2]));
		assertThat(expected.getStatus(), is(dataPoint[3]));
		assertThat(expected.getPropStats(), is(dataPoint[4]));
		assertThat(expected.getError(), is(dataPoint[5]));
		assertThat(expected.getResponseDescription(), is(dataPoint[6]));
		assertThat(expected.getLocation(), is(dataPoint[7]));
	}

	@Override
	protected Response getInstance() {
		return new Response(STATUS, ERROR, RESPONSE_DESCRIPTION, LOCATION, HREF);
	}

	@Override
	protected String getString() {
		return "Response[Status[HTTP/1.1 207 Multi-Status], [], Error[[Prop[[]]]], ResponseDescription[X], Location[HRef[http://localhost]]]";
	}

}
