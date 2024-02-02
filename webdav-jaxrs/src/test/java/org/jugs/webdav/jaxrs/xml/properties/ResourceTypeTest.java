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

package org.jugs.webdav.jaxrs.xml.properties;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;
import org.jugs.webdav.jaxrs.xml.elements.Collection;
import org.jugs.webdav.util.Utilities;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link ResourceType}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ResourceTypeTest extends AbstractJaxbCoreFunctionality<ResourceType> {

	private static final Collection RESOURCE_TYPE = Utilities.buildInstanceOf(Collection.class);

	@Test
	void constructorDoesNotAcceptNullAsLockEntries() {
		assertThrows(NullArgumentException.class, () -> new ResourceType((Object[]) null));
	}

	private static final Object[] RESOURCETYPE = { ResourceType.RESOURCETYPE, "<D:resourcetype xmlns:D=\"DAV:\"/>", EMPTY_LIST };
	private static final Object[] COLLECTION = { ResourceType.COLLECTION, "<D:resourcetype xmlns:D=\"DAV:\"><D:collection/></D:resourcetype>",
			asList(RESOURCE_TYPE) };
	private static final Object[] RESOURCETYPE_CONSTRUCTOR = { new ResourceType(RESOURCE_TYPE),
			"<D:resourcetype xmlns:D=\"DAV:\"><D:collection/></D:resourcetype>", asList(RESOURCE_TYPE) };

	@Test
	void marshallingResourcetype() throws JAXBException {
		marshalling(RESOURCETYPE);
	}

	@Test
	void unmarshallingResourcetype() throws JAXBException {
		unmarshalling(RESOURCETYPE);
	}

	@Test
	void marshallingCollection() throws JAXBException {
		marshalling(COLLECTION);
	}

	@Test
	void unmarshallingCollection() throws JAXBException {
		unmarshalling(COLLECTION);
	}

	@Test
	void marshallingResourcetypeConstructor() throws JAXBException {
		marshalling(RESOURCETYPE_CONSTRUCTOR);
	}

	@Test
	void unmarshallingResourcetypeConstructor() throws JAXBException {
		unmarshalling(RESOURCETYPE_CONSTRUCTOR);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final ResourceType actual, final ResourceType expected, final Object[] dataPoint) {
		assertThat(actual.getResourceTypes(), is(dataPoint[2]));
		assertThat(expected.getResourceTypes(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public ResourceType resourcetype;
	}

	@Test
	void shouldUnmarshalRESOURCETYPEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:resourcetype/>";
		// when
		final ResourceType unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).resourcetype;
		// then
		assertThat(unmarshalledInstance, is(sameInstance(ResourceType.RESOURCETYPE)));
	}

	@Test
	void shouldUnmarshalCOLLECTIONConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:resourcetype><D:collection/></D:resourcetype>";
		// when
		final ResourceType unmarshalledInstance = ((X) JAXBContext.newInstance(X.class, Collection.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).resourcetype;
		// then
		assertThat(unmarshalledInstance, is(sameInstance(ResourceType.COLLECTION)));
	}

	@Override
	protected ResourceType getInstance() {
		return ResourceType.COLLECTION;
	}

	@Override
	protected String getString() {
		return "ResourceType[[Collection[]]]";
	}

}
