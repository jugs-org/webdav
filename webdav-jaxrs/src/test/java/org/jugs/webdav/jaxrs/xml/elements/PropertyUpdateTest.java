/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2022 Java User Group Stuttgart
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
import org.junit.experimental.theories.DataPoint;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link PropertyUpdate}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class PropertyUpdateTest extends AbstractJaxbCoreFunctionality<PropertyUpdate> {

	private static final Prop PROP = new Prop();
	private static final Remove REMOVE = new Remove(PROP);
	private static final Set SET = new Set(PROP);

	@Test
	void constructorDoesNotAcceptNull() {
		assertThrows(NullArgumentException.class, () -> new PropertyUpdate(null));
	}

	@DataPoint
	public static final Object[] SINGLE_UPDATE = { new PropertyUpdate(REMOVE),
			"<D:propertyupdate xmlns:D=\"DAV:\"><D:remove><D:prop/></D:remove></D:propertyupdate>", asList(REMOVE) };

	@DataPoint
	public static final Object[] MULTIPLE_UPDATES = { new PropertyUpdate(SET, REMOVE, SET),
			"<D:propertyupdate xmlns:D=\"DAV:\"><D:set><D:prop/></D:set><D:remove><D:prop/></D:remove><D:set><D:prop/></D:set></D:propertyupdate>",
			asList(SET, REMOVE, SET) };

	@Test
	void marshallingSingleUpdate() throws JAXBException {
		marshalling(SINGLE_UPDATE);
	}

	@Test
	void unmarshallingSingleUpdate() throws JAXBException {
		unmarshalling(SINGLE_UPDATE);
	}

	@Test
	void marshallingMultipleUpdate() throws JAXBException {
		marshalling(MULTIPLE_UPDATES);
	}

	@Test
	void unmarshallingMultipleUpdate() throws JAXBException {
		unmarshalling(MULTIPLE_UPDATES);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final PropertyUpdate actual, final PropertyUpdate expected, final Object[] dataPoint) {
		assertThat(actual.list(), is(dataPoint[2]));
		assertThat(expected.list(), is(dataPoint[2]));
	}

	@Override
	protected PropertyUpdate getInstance() {
		return new PropertyUpdate(SET);
	}

	@Override
	protected String getString() {
		return "PropertyUpdate[[Set[Prop[[]]]]]";
	}

}
