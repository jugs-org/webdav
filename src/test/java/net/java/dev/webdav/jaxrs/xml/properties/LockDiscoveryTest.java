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

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;
import net.java.dev.webdav.jaxrs.xml.elements.ActiveLock;
import net.java.dev.webdav.jaxrs.xml.elements.Depth;
import net.java.dev.webdav.jaxrs.xml.elements.HRef;
import net.java.dev.webdav.jaxrs.xml.elements.LockRoot;
import net.java.dev.webdav.jaxrs.xml.elements.LockScope;
import net.java.dev.webdav.jaxrs.xml.elements.LockToken;
import net.java.dev.webdav.jaxrs.xml.elements.LockType;
import net.java.dev.webdav.jaxrs.xml.elements.Owner;
import net.java.dev.webdav.jaxrs.xml.elements.TimeOut;
import net.java.dev.webdav.util.Utilities;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link LockDiscovery}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockDiscoveryTest extends AbstractJaxbCoreFunctionality<LockDiscovery> {
	private static ActiveLock ACTIVE_LOCK = Utilities.buildInstanceOf(ActiveLock.class);

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsLockEntries() {
		new LockDiscovery((ActiveLock[]) null);
	}

	@DataPoint
	public static final Object[] LOCKDISCOVERY = { LockDiscovery.LOCKDISCOVERY, "<D:lockdiscovery xmlns:D=\"DAV:\"/>", EMPTY_LIST };

	@DataPoint
	public static final Object[] ACTIVELOCKS_CONSTRUCTOR = { new LockDiscovery(ACTIVE_LOCK),
			"<D:lockdiscovery xmlns:D=\"DAV:\"><D:activelock/></D:lockdiscovery>", asList(ACTIVE_LOCK) };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final LockDiscovery actual, final LockDiscovery expected, final Object[] dataPoint) {
		assertThat(actual.getActiveLocks(), is(dataPoint[2]));
		assertThat(expected.getActiveLocks(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public LockDiscovery lockdiscovery;
	}

	@Test
	public final void shouldUnmarshalGETLCOKDISCOVERYConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:lockdiscovery/>";

		// when
		final LockDiscovery unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).lockdiscovery;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(LockDiscovery.LOCKDISCOVERY)));
	}

	@Override
	protected final LockDiscovery getInstance() {
		return new LockDiscovery(new ActiveLock(LockScope.SHARED, LockType.WRITE, Depth.ZERO, new Owner(""), TimeOut.INFINITE, new LockToken(new HRef(
				"http://localhost")), new LockRoot(new HRef("http://localhost"))));
	}
}
