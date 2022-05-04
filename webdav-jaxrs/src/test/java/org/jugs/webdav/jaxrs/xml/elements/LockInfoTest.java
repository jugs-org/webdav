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

import static org.jugs.webdav.jaxrs.xml.elements.LockScope.EXCLUSIVE;
import static org.jugs.webdav.jaxrs.xml.elements.LockType.WRITE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link Include}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockInfoTest extends AbstractJaxbCoreFunctionality<LockInfo> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullLockScope() {
		new LockInfo(null, WRITE, null);
	}

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullLockType() {
		new LockInfo(EXCLUSIVE, null, null);
	}

	@DataPoint
	public static final Object[] NO_OWNER = { new LockInfo(EXCLUSIVE, WRITE, null),
			"<D:lockinfo xmlns:D=\"DAV:\"><D:lockscope><D:exclusive/></D:lockscope><D:locktype><D:write/></D:locktype></D:lockinfo>", EXCLUSIVE, WRITE, null };

	@DataPoint
	public static final Object[] HAS_OWNER = { new LockInfo(EXCLUSIVE, WRITE, new Owner()),
			"<D:lockinfo xmlns:D=\"DAV:\"><D:lockscope><D:exclusive/></D:lockscope><D:locktype><D:write/></D:locktype><D:owner/></D:lockinfo>", EXCLUSIVE,
			WRITE, new Owner() };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final LockInfo actual, final LockInfo expected, final Object[] dataPoint) {
		assertThat(actual.getLockScope(), is(dataPoint[2]));
		assertThat(actual.getLockType(), is(dataPoint[3]));
		assertThat(actual.getOwner(), is(dataPoint[4]));
		assertThat(expected.getLockScope(), is(dataPoint[2]));
		assertThat(expected.getLockType(), is(dataPoint[3]));
		assertThat(expected.getOwner(), is(dataPoint[4]));
	}

	@Override
	protected final LockInfo getInstance() {
		return new LockInfo(EXCLUSIVE, WRITE, null);
	}

	@Override
	protected final String getString() {
		return "LockInfo[LockScope[null, Exclusive[]], LockType[Write[]], null]";
	}
}
