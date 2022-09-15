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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link PropFind}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class PropFindTest extends AbstractJaxbCoreFunctionality<PropFind> {

	private static final PropName PROPNAME = PropName.PROPNAME;
	private static final AllProp ALLPROP = AllProp.ALLPROP;
	private static final Include INCLUDE = new Include();
	private static final Prop PROP = new Prop();

	@Test
	void constructorDoesNotAcceptNullPropName() {
		assertThrows(NullArgumentException.class, () -> new PropFind((PropName) null));
	}

	@Test
	void constructorDoesNotAcceptNullAllProp() {
		assertThrows(NullArgumentException.class, () -> new PropFind((AllProp) null));
	}

	@Test
	void constructorDoesNotAcceptNullProp() {
		assertThrows(NullArgumentException.class, () -> new PropFind((Prop) null));
	}

	@DataPoint
	public static final Object[] PROPNAME_VARIANT = { new PropFind(PROPNAME), "<D:propfind xmlns:D=\"DAV:\"><D:propname/></D:propfind>", PROPNAME, null, null,
			null };

	@DataPoint
	public static final Object[] ALLPROP_VARIANT = { new PropFind(ALLPROP), "<D:propfind xmlns:D=\"DAV:\"><D:allprop/></D:propfind>", null, ALLPROP, null, null };

	@DataPoint
	public static final Object[] ALLPROP_INCLUDE_VARIANT = { new PropFind(ALLPROP, INCLUDE),
			"<D:propfind xmlns:D=\"DAV:\"><D:allprop/><D:include/></D:propfind>", null, ALLPROP, INCLUDE, null };

	@DataPoint
	public static final Object[] PROP_VARIANT = { new PropFind(PROP), "<D:propfind xmlns:D=\"DAV:\"><D:prop/></D:propfind>", null, null, null, PROP };

	@Test
	void marshallingPropname() throws JAXBException {
		marshalling(PROPNAME_VARIANT);
	}

	@Test
	void unmarshallingPropname() throws JAXBException {
		unmarshalling(PROPNAME_VARIANT);
	}

	@Test
	void marshallingAllProp() throws JAXBException {
		marshalling(ALLPROP_VARIANT);
	}

	@Test
	void unmarshallingAllProp() throws JAXBException {
		unmarshalling(ALLPROP_VARIANT);
	}

	@Test
	void marshallingAllPropInclude() throws JAXBException {
		marshalling(ALLPROP_INCLUDE_VARIANT);
	}

	@Test
	void unmarshallingAllPropInclude() throws JAXBException {
		unmarshalling(ALLPROP_INCLUDE_VARIANT);
	}

	@Test
	void marshallingProp() throws JAXBException {
		marshalling(PROP_VARIANT);
	}

	@Test
	void unmarshallingProp() throws JAXBException {
		unmarshalling(PROP_VARIANT);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final PropFind actual, final PropFind expected, final Object[] dataPoint) {
		assertThat(actual.getPropName(), is(dataPoint[2]));
		assertThat(actual.getAllProp(), is(dataPoint[3]));
		assertThat(actual.getInclude(), is(dataPoint[4]));
		assertThat(actual.getProp(), is(dataPoint[5]));
		assertThat(expected.getPropName(), is(dataPoint[2]));
		assertThat(expected.getAllProp(), is(dataPoint[3]));
		assertThat(expected.getInclude(), is(dataPoint[4]));
		assertThat(expected.getProp(), is(dataPoint[5]));
	}

	@Override
	protected PropFind getInstance() {
		return new PropFind(ALLPROP);
	}

	@Override
	protected String getString() {
		return "PropFind[null, AllProp[], null, null]";
	}
}
