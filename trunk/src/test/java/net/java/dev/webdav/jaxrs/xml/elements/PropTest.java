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

import java.util.Arrays;

import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;
import net.java.dev.webdav.jaxrs.xml.properties.CreationDate;

import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link Prop}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class PropTest extends AbstractJaxbCoreFunctionality<Prop> {
	@DataPoint
	public static final Object[] DATA_POINT = { new Prop(new CreationDate()), "<D:prop xmlns:D=\"DAV:\"><D:creationdate/></D:prop>",
			Arrays.asList(new CreationDate()) };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final Prop actual, final Prop expected, final Object[] dataPoint) {
		assertThat(actual.getProperties(), is(dataPoint[2]));
		assertThat(expected.getProperties(), is(dataPoint[2]));
	}
}
