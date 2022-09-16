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
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;

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
public final class LockEntryTest extends AbstractJaxbCoreFunctionality<LockEntry> {

	@Test
	void constructorDoesNotAcceptNullLockScope() {
		assertThrows(NullArgumentException.class, () -> new LockEntry(null, WRITE));
	}

	@Test
	public void constructorDoesNotAcceptNullLockType() {
		assertThrows(NullArgumentException.class, () -> new LockEntry(EXCLUSIVE, null));
	}

	private static final Object[] STANDARD = { new LockEntry(EXCLUSIVE, WRITE),
			"<D:lockentry xmlns:D=\"DAV:\"><D:lockscope><D:exclusive/></D:lockscope><D:locktype><D:write/></D:locktype></D:lockentry>", EXCLUSIVE, WRITE };

	@Test
	void testMarshalling() throws JAXBException {
		marshalling(STANDARD);
	}

	@Test
	void testUnmarshalling() throws JAXBException {
		unmarshalling(STANDARD);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final LockEntry actual, final LockEntry expected, final Object[] dataPoint) {
		assertThat(actual.getLockScope(), is(dataPoint[2]));
		assertThat(actual.getLockType(), is(dataPoint[3]));
		assertThat(expected.getLockScope(), is(dataPoint[2]));
		assertThat(expected.getLockType(), is(dataPoint[3]));
	}

	@Override
	protected LockEntry getInstance() {
		return new LockEntry(EXCLUSIVE, WRITE);
	}

	@Override
	protected String getString() {
		return "LockEntry[LockScope[null, Exclusive[]], LockType[Write[]]]";
	}

}
