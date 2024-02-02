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
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBException;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link Prop}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class PropTest extends AbstractJaxbCoreFunctionality<Prop> {

	private static final CreationDate CREATIONDATE = CreationDate.CREATIONDATE;

	private static final Object[] DATA_POINT = { new Prop(CREATIONDATE), "<D:prop xmlns:D=\"DAV:\"><D:creationdate/></D:prop>", singletonList(CREATIONDATE) };

	@Test
	void testMarshalling() throws JAXBException {
		marshalling(DATA_POINT);
	}

	@Test
	void testUnmarshalling() throws JAXBException {
		unmarshalling(DATA_POINT);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final Prop actual, final Prop expected, final Object[] dataPoint) {
		assertThat(actual.getProperties(), is(dataPoint[2]));
		assertThat(expected.getProperties(), is(dataPoint[2]));
	}

	@Override
	protected Prop getInstance() {
		return new Prop(CREATIONDATE);
	}

	@Override
	protected String getString() {
		return "Prop[[CreationDate[null]]]";
	}

}
