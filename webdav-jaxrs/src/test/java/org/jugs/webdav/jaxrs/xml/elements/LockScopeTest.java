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

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.io.StringReader;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link LockScope}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockScopeTest extends AbstractJaxbCoreFunctionality<LockScope> {

	private static final Object[] EXCLUSIVE = { LockScope.EXCLUSIVE, "<D:lockscope xmlns:D=\"DAV:\"><D:exclusive/></D:lockscope>" };
	private static final Object[] SHARED = { LockScope.SHARED, "<D:lockscope xmlns:D=\"DAV:\"><D:shared/></D:lockscope>" };

	@Test
	void marshallingExclusive() throws JAXBException {
		marshalling(EXCLUSIVE);
	}

	@Test
	void unmarshallingExclusive() throws JAXBException {
		unmarshalling(EXCLUSIVE);
	}

	@Test
	void marshallingShared() throws JAXBException {
		marshalling(SHARED);
	}

	@Test
	void unmarshallingShared() throws JAXBException {
		unmarshalling(SHARED);
	}

	/**
	 * JAXB Workaround: JAXB only invokes {@link XmlAdapter} if element is wrapped.
	 */
	@XmlRootElement
	private static final class X {
		public LockScope lockscope;
	}

	@Test
	void shouldUnmarshalEXCLUSIVEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:lockscope><D:exclusive/></D:lockscope>";

		// when
		final LockScope unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).lockscope;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(LockScope.EXCLUSIVE)));
	}

	@Test
	void shouldUnmarshalSHAREDConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:lockscope><D:shared/></D:lockscope>";

		// when
		final LockScope unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).lockscope;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(LockScope.SHARED)));
	}

	@Override
	protected LockScope getInstance() {
		return LockScope.EXCLUSIVE;
	}

	@Override
	protected String getString() {
		return "LockScope[null, Exclusive[]]";
	}

}
