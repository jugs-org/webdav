/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2013 The java.net WebDAV Project
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
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link ResponseDescription}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ResponseDescriptionTest extends AbstractJaxbCoreFunctionality<ResponseDescription> {
	@DataPoint
	public static final Object[] DATA_POINT = { new ResponseDescription("some content"),
			"<D:responsedescription xmlns:D=\"DAV:\">some content</D:responsedescription>", "some content" };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final ResponseDescription actual, final ResponseDescription expected, final Object[] dataPoint) {
		assertThat(actual.getContent(), is(dataPoint[2]));
		assertThat(expected.getContent(), is(dataPoint[2]));
	}
}
