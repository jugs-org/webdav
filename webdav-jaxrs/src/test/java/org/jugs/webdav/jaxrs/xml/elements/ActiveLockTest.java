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
import org.jugs.webdav.util.Utilities;
import org.junit.experimental.theories.DataPoint;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jugs.webdav.jaxrs.xml.elements.Depth.INFINITY;
import static org.jugs.webdav.jaxrs.xml.elements.LockScope.EXCLUSIVE;
import static org.jugs.webdav.jaxrs.xml.elements.LockType.WRITE;
import static org.jugs.webdav.jaxrs.xml.elements.TimeOut.INFINITE;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link ActiveLock}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ActiveLockTest extends AbstractJaxbCoreFunctionality<ActiveLock> {
	private static final HRef HREF = new HRef("http://localhost");
	private static final LockScope LOCK_SCOPE = EXCLUSIVE;
	private static final LockType LOCK_TYPE = WRITE;
	private static final Depth DEPTH = INFINITY;
	private static final Owner OWNER = Utilities.buildInstanceOf(Owner.class);
	private static final TimeOut TIMEOUT = INFINITE;
	private static final LockToken LOCK_TOKEN = new LockToken(HREF);
	private static final LockRoot LOCK_ROOT = new LockRoot(HREF);

	@Test
	void constructorDoesNotAcceptNullLockScope() {
		assertThrows(NullArgumentException.class, () -> new ActiveLock(null, LOCK_TYPE, DEPTH, OWNER, TIMEOUT, LOCK_TOKEN, LOCK_ROOT));
	}

	@Test
	void constructorDoesNotAcceptNullLockType() {
		assertThrows(NullArgumentException.class, () -> new ActiveLock(LOCK_SCOPE, null, DEPTH, OWNER, TIMEOUT, LOCK_TOKEN, LOCK_ROOT));
	}

	@Test
	void constructorDoesNotAcceptNullDepth() {
		assertThrows(NullArgumentException.class, () -> new ActiveLock(LOCK_SCOPE, LOCK_TYPE, null, OWNER, TIMEOUT, LOCK_TOKEN, LOCK_ROOT));
	}

	@Test
	public final void constructorDoesNotAcceptNullLockRoot() {
		assertThrows(NullArgumentException.class, () -> new ActiveLock(LOCK_SCOPE, LOCK_TYPE, DEPTH, OWNER, TIMEOUT, LOCK_TOKEN, null));
	}

	@DataPoint
	public static final Object[] ALL_PARAMS = {
			new ActiveLock(LOCK_SCOPE, LOCK_TYPE, DEPTH, OWNER, TIMEOUT, LOCK_TOKEN, LOCK_ROOT),
			"<D:activelock xmlns:D=\"DAV:\"><D:lockscope><D:exclusive/></D:lockscope><D:locktype><D:write/></D:locktype><D:depth>infinity</D:depth><D:owner/><D:timeout>Infinite</D:timeout><D:locktoken><D:href>http://localhost</D:href></D:locktoken><D:lockroot><D:href>http://localhost</D:href></D:lockroot></D:activelock>",
			LOCK_SCOPE, LOCK_TYPE, DEPTH, OWNER, TIMEOUT, LOCK_TOKEN, LOCK_ROOT };

	@DataPoint
	public static final Object[] MINIMUM_PARAMS = {
			new ActiveLock(LOCK_SCOPE, LOCK_TYPE, DEPTH, null, null, null, LOCK_ROOT),
			"<D:activelock xmlns:D=\"DAV:\"><D:lockscope><D:exclusive/></D:lockscope><D:locktype><D:write/></D:locktype><D:depth>infinity</D:depth><D:lockroot><D:href>http://localhost</D:href></D:lockroot></D:activelock>",
			LOCK_SCOPE, LOCK_TYPE, DEPTH, null, null, null, LOCK_ROOT };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final ActiveLock actual, final ActiveLock expected, final Object[] dataPoint) {
		assertThat(actual.getLockScope(), is(dataPoint[2]));
		assertThat(actual.getLockType(), is(dataPoint[3]));
		assertThat(actual.getDepth(), is(dataPoint[4]));
		assertThat(actual.getOwner(), is(dataPoint[5]));
		assertThat(actual.getTimeOut(), is(dataPoint[6]));
		assertThat(actual.getLockToken(), is(dataPoint[7]));
		assertThat(actual.getLockRoot(), is(dataPoint[8]));
		assertThat(expected.getLockScope(), is(dataPoint[2]));
		assertThat(expected.getLockType(), is(dataPoint[3]));
		assertThat(expected.getDepth(), is(dataPoint[4]));
		assertThat(expected.getOwner(), is(dataPoint[5]));
		assertThat(expected.getTimeOut(), is(dataPoint[6]));
		assertThat(expected.getLockToken(), is(dataPoint[7]));
		assertThat(expected.getLockRoot(), is(dataPoint[8]));
	}

	@Override
	protected final ActiveLock getInstance() {
		return new ActiveLock(LOCK_SCOPE, LOCK_TYPE, DEPTH, null, null, null, LOCK_ROOT);
	}

	@Override
	protected final String getString() {
		return "ActiveLock[LockScope[null, Exclusive[]], null, INFINITY, null, null, null, LockRoot[HRef[http://localhost]]]";
	}
}
