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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jugs.webdav.jaxrs.xml.elements.LockScope.EXCLUSIVE;
import static org.jugs.webdav.jaxrs.xml.elements.LockType.WRITE;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link Include}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockInfoTest extends AbstractJaxbCoreFunctionality<LockInfo> {

	@Test
	void constructorDoesNotAcceptNullLockScope() {
		assertThrows(NullArgumentException.class, () -> new LockInfo(null, WRITE, null));
	}

	@Test
	void constructorDoesNotAcceptNullLockType() {
		assertThrows(NullArgumentException.class, () -> new LockInfo(EXCLUSIVE, null, null));
	}

	private static final Object[] NO_OWNER = { new LockInfo(EXCLUSIVE, WRITE, null),
			"<D:lockinfo xmlns:D=\"DAV:\"><D:lockscope><D:exclusive/></D:lockscope><D:locktype><D:write/></D:locktype></D:lockinfo>", EXCLUSIVE, WRITE, null };
	private static final Object[] HAS_OWNER = { new LockInfo(EXCLUSIVE, WRITE, new Owner()),
			"<D:lockinfo xmlns:D=\"DAV:\"><D:lockscope><D:exclusive/></D:lockscope><D:locktype><D:write/></D:locktype><D:owner/></D:lockinfo>", EXCLUSIVE,
			WRITE, new Owner() };

	@Test
	void marshallingNoOwner() throws JAXBException {
		marshalling(NO_OWNER);
	}

	@Test
	void unmarshallingNoOwner() throws JAXBException {
		unmarshalling(NO_OWNER);
	}

	@Test
	void marshallingHasOwner() throws JAXBException {
		marshalling(HAS_OWNER);
	}

	@Test
	void unmarshallingHasOwner() throws JAXBException {
		unmarshalling(HAS_OWNER);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final LockInfo actual, final LockInfo expected, final Object[] dataPoint) {
		assertThat(actual.getLockScope(), is(dataPoint[2]));
		assertThat(actual.getLockType(), is(dataPoint[3]));
		assertThat(actual.getOwner(), is(dataPoint[4]));
		assertThat(expected.getLockScope(), is(dataPoint[2]));
		assertThat(expected.getLockType(), is(dataPoint[3]));
		assertThat(expected.getOwner(), is(dataPoint[4]));
	}

	@Override
	protected LockInfo getInstance() {
		return new LockInfo(EXCLUSIVE, WRITE, null);
	}

	@Override
	protected String getString() {
		return "LockInfo[LockScope[null, Exclusive[]], LockType[Write[]], null]";
	}

}
