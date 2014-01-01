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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;
import net.java.dev.webdav.util.Utilities;

import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link MultiStatus}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class MultiStatusTest extends AbstractJaxbCoreFunctionality<MultiStatus> {
	private static final Response RESPONSE = Utilities.buildInstanceOf(Response.class);
	private static final ResponseDescription RESPONSE_DESCRIPTION = new ResponseDescription("X");

	@DataPoint
	public static final Object[] SINGLE_RESPONSE_ONLY = { new MultiStatus(RESPONSE), "<D:multistatus xmlns:D=\"DAV:\"><D:response/></D:multistatus>",
			asList(RESPONSE), null };

	@DataPoint
	public static final Object[] SINGLE_RESPONSE_WITH_RESPONSE_DESCRIPTION = { new MultiStatus(RESPONSE_DESCRIPTION, RESPONSE),
			"<D:multistatus xmlns:D=\"DAV:\"><D:response/><D:responsedescription>X</D:responsedescription></D:multistatus>", asList(RESPONSE),
			RESPONSE_DESCRIPTION };

	@DataPoint
	public static final Object[] MULTI_RESPONSE_WITH_RESPONSE_DESCRIPTION = { new MultiStatus(RESPONSE_DESCRIPTION, RESPONSE, RESPONSE),
			"<D:multistatus xmlns:D=\"DAV:\"><D:response/><D:response/><D:responsedescription>X</D:responsedescription></D:multistatus>",
			asList(RESPONSE, RESPONSE), RESPONSE_DESCRIPTION };

	@DataPoint
	public static final Object[] RESPONSE_DESCRIPTION_ONLY = { new MultiStatus(RESPONSE_DESCRIPTION),
			"<D:multistatus xmlns:D=\"DAV:\"><D:responsedescription>X</D:responsedescription></D:multistatus>", EMPTY_LIST, RESPONSE_DESCRIPTION };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final MultiStatus actual, final MultiStatus expected, final Object[] dataPoint) {
		assertThat(actual.getResponses(), is(dataPoint[2]));
		assertThat(actual.getResponseDescription(), is(dataPoint[3]));
		assertThat(expected.getResponses(), is(dataPoint[2]));
		assertThat(expected.getResponseDescription(), is(dataPoint[3]));
	}
}
