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

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static net.java.dev.webdav.jaxrs.ResponseStatus.MULTI_STATUS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;

import javax.ws.rs.core.Response.StatusType;

import net.java.dev.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import net.java.dev.webdav.jaxrs.NullArgumentException;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link Response}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ResponseTest extends AbstractJaxbCoreFunctionality<Response> {
	private static final HRef HREF = new HRef("http://localhost");
	private static final Status STATUS = new Status((StatusType) MULTI_STATUS);
	private static final Error ERROR = new Error(new Prop());
	private static final ResponseDescription RESPONSE_DESCRIPTION = new ResponseDescription("X");
	private static final Location LOCATION = new Location(HREF);
	private static final PropStat PROP_STAT = new PropStat(new Prop(), STATUS);

	@Test(expected = NullArgumentException.class)
	public final void constructorShouldNotAcceptNullHRef() {
		new Response(STATUS, ERROR, RESPONSE_DESCRIPTION, LOCATION, null);
	}

	@Test(expected = NullArgumentException.class)
	public final void constructorShouldNotAcceptNullStatus() {
		new Response(null, ERROR, RESPONSE_DESCRIPTION, LOCATION, HREF);
	}

	@Test(expected = NullArgumentException.class)
	public final void constructorShouldNotAcceptNullPropStats() {
		new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, (PropStat) null);
	}

	@SuppressWarnings("deprecation")
	@Test(expected = NullArgumentException.class)
	public final void constructorShouldNotAcceptNullPropStatCollection() {
		new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, (java.util.Collection<PropStat>) null);
	}

	@SuppressWarnings("deprecation")
	@Test(expected = NullArgumentException.class)
	public final void constructorShouldNotAcceptEmptyPropStatCollection() {
		new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, new LinkedList<PropStat>());
	}

	@DataPoint
	public static final Object[] STATUS_VARIANT = {
			new Response(STATUS, ERROR, RESPONSE_DESCRIPTION, LOCATION, HREF),
			"<D:response xmlns:D=\"DAV:\"><D:href>http://localhost</D:href><D:status>HTTP/1.1 207 Multi-Status</D:status><D:error><D:prop/></D:error><D:responsedescription>X</D:responsedescription><D:location><D:href>http://localhost</D:href></D:location></D:response>",
			asList(HREF), STATUS, EMPTY_LIST, ERROR, RESPONSE_DESCRIPTION, LOCATION };

	@DataPoint
	public static final Object[] PROPSTATS_VARIANT = {
			new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, PROP_STAT, PROP_STAT, PROP_STAT),
			"<D:response xmlns:D=\"DAV:\"><D:href>http://localhost</D:href><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:error><D:prop/></D:error><D:responsedescription>X</D:responsedescription><D:location><D:href>http://localhost</D:href></D:location></D:response>",
			asList(HREF), null, asList(PROP_STAT, PROP_STAT, PROP_STAT), ERROR, RESPONSE_DESCRIPTION, LOCATION };

	@SuppressWarnings("deprecation")
	@DataPoint
	public static final Object[] DEPRECATED_PROPSTATS_VARIANT = {
			new Response(HREF, ERROR, RESPONSE_DESCRIPTION, LOCATION, asList(PROP_STAT, PROP_STAT, PROP_STAT)),
			"<D:response xmlns:D=\"DAV:\"><D:href>http://localhost</D:href><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:propstat><D:prop/><D:status>HTTP/1.1 207 Multi-Status</D:status></D:propstat><D:error><D:prop/></D:error><D:responsedescription>X</D:responsedescription><D:location><D:href>http://localhost</D:href></D:location></D:response>",
			asList(HREF), null, asList(PROP_STAT, PROP_STAT, PROP_STAT), ERROR, RESPONSE_DESCRIPTION, LOCATION };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final Response actual, final Response expected, final Object[] dataPoint) {
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
	protected final Response getInstance() {
		return new Response(STATUS, ERROR, RESPONSE_DESCRIPTION, LOCATION, HREF);
	}

	@Override
	protected final String getString() {
		return "Response[Status[HTTP/1.1 207 Multi-Status], [], Error[[Prop[[]]]], ResponseDescription[X], Location[HRef[http://localhost]]]";
	}
}
