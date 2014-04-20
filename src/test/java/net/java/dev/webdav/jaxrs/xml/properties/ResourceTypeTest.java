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

package net.java.dev.webdav.jaxrs.xml.properties;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.elements.Collection;
import net.java.dev.webdav.util.Utilities;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link ResourceType}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ResourceTypeTest extends AbstractJaxbCoreFunctionality<ResourceType> {
	private static Collection RESOURCE_TYPE = Utilities.buildInstanceOf(Collection.class);

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsLockEntries() {
		new ResourceType((Object[]) null);
	}

	@DataPoint
	public static final Object[] RESOURCETYPE = { ResourceType.RESOURCETYPE, "<D:resourcetype xmlns:D=\"DAV:\"/>", EMPTY_LIST };

	@DataPoint
	public static final Object[] COLLECTION = { ResourceType.COLLECTION, "<D:resourcetype xmlns:D=\"DAV:\"><D:collection/></D:resourcetype>",
			asList(RESOURCE_TYPE) };

	@DataPoint
	public static final Object[] RESOURCETYPE_CONSTRUCTOR = { new ResourceType(RESOURCE_TYPE),
			"<D:resourcetype xmlns:D=\"DAV:\"><D:collection/></D:resourcetype>", asList(RESOURCE_TYPE) };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final ResourceType actual, final ResourceType expected, final Object[] dataPoint) {
		assertThat(actual.getResourceTypes(), is(dataPoint[2]));
		assertThat(expected.getResourceTypes(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public ResourceType resourcetype;
	}

	@Test
	public final void shouldUnmarshalRESOURCETYPEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:resourcetype/>";

		// when
		final ResourceType unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).resourcetype;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(ResourceType.RESOURCETYPE)));
	}

	@Test
	public final void shouldUnmarshalCOLLECTIONConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:resourcetype><D:collection/></D:resourcetype>";

		// when
		final ResourceType unmarshalledInstance = ((X) JAXBContext.newInstance(X.class, Collection.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).resourcetype;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(ResourceType.COLLECTION)));
	}

	@Override
	protected final ResourceType getInstance() {
		return ResourceType.COLLECTION;
	}

	@Override
	protected final String getString() {
		return "ResourceType[[Collection[]]]";
	}
}
