/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2023 Java User Group Stuttgart
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
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.util.Utilities;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link LockDiscovery}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockDiscoveryTest extends AbstractJaxbCoreFunctionality<LockDiscovery> {

	private static ActiveLock ACTIVE_LOCK = Utilities.buildInstanceOf(ActiveLock.class);

	@Test
	void constructorDoesNotAcceptNullAsLockEntries() {
		assertThrows(NullArgumentException.class, () -> new LockDiscovery((ActiveLock[]) null));
	}

	private static final Object[] LOCKDISCOVERY = { LockDiscovery.LOCKDISCOVERY, "<D:lockdiscovery xmlns:D=\"DAV:\"/>", EMPTY_LIST };
	private static final Object[] ACTIVELOCKS_CONSTRUCTOR = { new LockDiscovery(ACTIVE_LOCK),
			"<D:lockdiscovery xmlns:D=\"DAV:\"><D:activelock/></D:lockdiscovery>", asList(ACTIVE_LOCK) };

	@Test
	void marshallingLockdiscovery() throws JAXBException {
		marshalling(LOCKDISCOVERY);
	}

	@Test
	void unmarshallingLockdiscovery() throws JAXBException {
		unmarshalling(LOCKDISCOVERY);
	}

	@Test
	void marshallingActivelocksConstructor() throws JAXBException {
		marshalling(ACTIVELOCKS_CONSTRUCTOR);
	}

	@Test
	void unmarshallingActivelocksConstructor() throws JAXBException {
		unmarshalling(ACTIVELOCKS_CONSTRUCTOR);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final LockDiscovery actual, final LockDiscovery expected, final Object[] dataPoint) {
		assertThat(actual.getActiveLocks(), is(dataPoint[2]));
		assertThat(expected.getActiveLocks(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public LockDiscovery lockdiscovery;
	}

	@Test
	void shouldUnmarshalGETLCOKDISCOVERYConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:lockdiscovery/>";
		// when
		final LockDiscovery unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).lockdiscovery;
		// then
		assertThat(unmarshalledInstance, is(sameInstance(LockDiscovery.LOCKDISCOVERY)));
	}

	@Override
	protected LockDiscovery getInstance() {
		return new LockDiscovery(new ActiveLock(LockScope.SHARED, LockType.WRITE, Depth.ZERO, new Owner(""), new TimeOut(75), new LockToken(new HRef(
				"http://localhost")), new LockRoot(new HRef("http://localhost"))));
	}

	@Override
	protected String getString() {
		return "LockDiscovery[[ActiveLock[LockScope[Shared[], null], LockToken[HRef[http://localhost]], ZERO, Owner[[]], TimeOut[75], LockToken[HRef[http://localhost]], LockRoot[HRef[http://localhost]]]]]";
	}

}
