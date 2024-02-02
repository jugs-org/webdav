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

import jakarta.ws.rs.core.Response.StatusType;
import jakarta.xml.bind.JAXBException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jugs.webdav.jaxrs.ResponseStatus.LOCKED;

/**
 * Unit test for {@link PropStat}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class PropStatTest extends AbstractJaxbCoreFunctionality<PropStat> {

	private static final Prop PROP = new Prop();
	private static final Status STATUS = new Status((StatusType) LOCKED);
	private static final Error ERROR = new Error(PROP);
	private static final ResponseDescription RESPONSEDESCRIPTION = new ResponseDescription("X");

	private static final Object[] PROP_STATUS = { new PropStat(PROP, STATUS),
			"<D:propstat xmlns:D=\"DAV:\"><D:prop/><D:status>HTTP/1.1 423 Locked</D:status></D:propstat>", PROP, STATUS, null, null };

	private static final Object[] PROP_STATUS_ERROR = { new PropStat(PROP, STATUS, ERROR),
			"<D:propstat xmlns:D=\"DAV:\"><D:prop/><D:status>HTTP/1.1 423 Locked</D:status><D:error><D:prop/></D:error></D:propstat>", PROP, STATUS, ERROR,
			null };

	private static final Object[] PROP_STATUS_ERROR_RESPONSEDESCRIPTION = {
			new PropStat(PROP, STATUS, ERROR, RESPONSEDESCRIPTION),
			"<D:propstat xmlns:D=\"DAV:\"><D:prop/><D:status>HTTP/1.1 423 Locked</D:status><D:error><D:prop/></D:error><D:responsedescription>X</D:responsedescription></D:propstat>",
			PROP, STATUS, ERROR, RESPONSEDESCRIPTION };

	@Test
	void marshallingStatus() throws JAXBException {
		marshalling(PROP_STATUS);
	}

	@Test
	void unmarshallingStatus() throws JAXBException {
		unmarshalling(PROP_STATUS);
	}

	@Test
	void marshallingStatusError() throws JAXBException {
		marshalling(PROP_STATUS_ERROR);
	}

	@Test
	void unmarshallingStatusError() throws JAXBException {
		unmarshalling(PROP_STATUS_ERROR);
	}

	@Test
	void marshallingStatusErrorResponsedescription() throws JAXBException {
		marshalling(PROP_STATUS_ERROR_RESPONSEDESCRIPTION);
	}

	@Test
	void unmarshallingStatusErrorResponsedescription() throws JAXBException {
		unmarshalling(PROP_STATUS_ERROR_RESPONSEDESCRIPTION);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final PropStat actual, final PropStat expected, final Object[] dataPoint) {
		assertThat(actual.getProp(), is(dataPoint[2]));
		assertThat(actual.getStatus(), is(dataPoint[3]));
		assertThat(actual.getError(), is(dataPoint[4]));
		assertThat(actual.getResponseDescription(), is(dataPoint[5]));
		assertThat(expected.getProp(), is(dataPoint[2]));
		assertThat(expected.getStatus(), is(dataPoint[3]));
		assertThat(expected.getError(), is(dataPoint[4]));
		assertThat(expected.getResponseDescription(), is(dataPoint[5]));
	}

	@Override
	protected PropStat getInstance() {
		return new PropStat(PROP, STATUS);
	}

	@Override
	protected String getString() {
		return "PropStat[Prop[[]], Status[HTTP/1.1 423 Locked], null, null]";
	}

}
