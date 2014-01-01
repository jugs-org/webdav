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

import static net.java.dev.webdav.jaxrs.xml.elements.LockScope.EXCLUSIVE;
import static net.java.dev.webdav.jaxrs.xml.elements.LockType.WRITE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link Include}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockEntryTest extends AbstractJaxbCoreFunctionality<LockEntry> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullLockScope() {
		new LockEntry(null, WRITE);
	}

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullLockType() {
		new LockEntry(EXCLUSIVE, null);
	}

	@DataPoint
	public static final Object[] STANDARD = { new LockEntry(EXCLUSIVE, WRITE),
			"<D:lockentry xmlns:D=\"DAV:\"><D:lockscope><D:exclusive/></D:lockscope><D:locktype><D:write/></D:locktype></D:lockentry>", EXCLUSIVE, WRITE };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final LockEntry actual, final LockEntry expected, final Object[] dataPoint) {
		assertThat(actual.getLockScope(), is(dataPoint[2]));
		assertThat(actual.getLockType(), is(dataPoint[3]));
		assertThat(expected.getLockScope(), is(dataPoint[2]));
		assertThat(expected.getLockType(), is(dataPoint[3]));
	}
}
