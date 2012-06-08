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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Long.MAX_VALUE;
import static net.java.dev.webdav.jaxrs.xml.elements.TimeOut.INFINITE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.experimental.theories.DataPoints;

/**
 * Unit test for {@link TimeOut}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class TimeOutTest extends AbstractJaxbCoreFunctionality<TimeOut> {
	@DataPoints
	public static final Object[][] DATA_POINTS = { { INFINITE, "<D:timeout xmlns:D=\"DAV:\">Infinite</D:timeout>", MAX_VALUE, TRUE },
			{ new TimeOut(1), "<D:timeout xmlns:D=\"DAV:\">Second-1</D:timeout>", 1L, FALSE } };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final TimeOut actual, final TimeOut expected, final Object[] dataPoint) {
		assertThat(actual.getSeconds(), is(dataPoint[2]));
		assertThat(actual.isInfinite(), is(dataPoint[3]));
		assertThat(expected.getSeconds(), is(dataPoint[2]));
		assertThat(expected.isInfinite(), is(dataPoint[3]));
	}
}
