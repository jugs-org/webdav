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
import net.java.dev.webdav.jaxrs.xml.elements.LockEntry;
import net.java.dev.webdav.jaxrs.xml.elements.LockScope;
import net.java.dev.webdav.jaxrs.xml.elements.LockType;
import net.java.dev.webdav.util.Utilities;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link SupportedLock}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class SupportedLockTest extends AbstractJaxbCoreFunctionality<SupportedLock> {
	private static LockEntry LOCK_ENTRY = Utilities.buildInstanceOf(LockEntry.class);

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsLockEntries() {
		new SupportedLock((LockEntry[]) null);
	}

	@DataPoint
	public static final Object[] SUPPORTEDLOCK = { SupportedLock.SUPPORTEDLOCK, "<D:supportedlock xmlns:D=\"DAV:\"/>", EMPTY_LIST };

	@DataPoint
	public static final Object[] LOCKENTRY_CONSTRUCTOR = { new SupportedLock(LOCK_ENTRY), "<D:supportedlock xmlns:D=\"DAV:\"><D:lockentry/></D:supportedlock>",
			asList(LOCK_ENTRY) };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final SupportedLock actual, final SupportedLock expected, final Object[] dataPoint) {
		assertThat(actual.getLockEntries(), is(dataPoint[2]));
		assertThat(expected.getLockEntries(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public SupportedLock supportedlock;
	}

	@Test
	public final void shouldUnmarshalSUPPORTEDLOCKConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:supportedlock/>";

		// when
		final SupportedLock unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).supportedlock;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(SupportedLock.SUPPORTEDLOCK)));
	}

	@Override
	protected final SupportedLock getInstance() {
		return new SupportedLock(new LockEntry(LockScope.EXCLUSIVE, LockType.WRITE));
	}

	@Override
	protected final String getString() {
		return "SupportedLock[[LockEntry[LockScope[null, Exclusive[]], LockType[Write[]]]]]";
	}
}
