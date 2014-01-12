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

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link LockScope}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockScopeTest extends AbstractJaxbCoreFunctionality<LockScope> {
	@DataPoint
	public static final Object[] EXCLUSIVE = { LockScope.EXCLUSIVE, "<D:lockscope xmlns:D=\"DAV:\"><D:exclusive/></D:lockscope>" };

	@DataPoint
	public static final Object[] SHARED = { LockScope.SHARED, "<D:lockscope xmlns:D=\"DAV:\"><D:shared/></D:lockscope>" };

	/**
	 * JAXB Workaround: JAXB only invokes {@link XmlAdapter} if element is wrapped.
	 */
	@XmlRootElement
	private static final class X {
		public LockScope lockscope;
	}

	@Test
	public final void shouldUnmarshalEXCLUSIVEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:lockscope><D:exclusive/></D:lockscope>";

		// when
		final LockScope unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).lockscope;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(LockScope.EXCLUSIVE)));
	}

	@Test
	public final void shouldUnmarshalSHAREDConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:lockscope><D:shared/></D:lockscope>";

		// when
		final LockScope unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).lockscope;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(LockScope.SHARED)));
	}

	@Override
	protected final LockScope getInstance() {
		return LockScope.EXCLUSIVE;
	}

	@Override
	protected final String getString() {
		return "LockScope[null, Exclusive[]]";
	}
}
