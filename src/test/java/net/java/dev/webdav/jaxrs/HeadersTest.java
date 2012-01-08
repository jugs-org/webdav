/*
 * Copyright 2012 Markus KARG
 *
 * This file is part of webdav-jaxrs.
 *
 * webdav-jaxrs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * webdav-jaxrs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with webdav-jaxrs.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.java.dev.webdav.jaxrs;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests WebDAV headers.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class HeadersTest {
	@Test
	public final void testHeaders() {
		assertThat(Headers.DAV, is(equalTo("DAV")));
		assertThat(Headers.DEPTH, is(equalTo("Depth")));
		assertThat(Headers.DEPTH_0, is(equalTo("0")));
		assertThat(Headers.DEPTH_1, is(equalTo("1")));
		assertThat(Headers.DEPTH_INFINITY, is(equalTo("infinity")));
		assertThat(Headers.DESTINATION, is(equalTo("Destination")));
		assertThat(Headers.IF, is(equalTo("If")));
		assertThat(Headers.LOCK_TOKEN, is(equalTo("Lock-Token")));
		assertThat(Headers.OVERWRITE, is(equalTo("Overwrite")));
		assertThat(Headers.OVERWRITE_TRUE, is(equalTo("T")));
		assertThat(Headers.OVERWRITE_FALSE, is(equalTo("F")));
		assertThat(Headers.TIMEOUT, is(equalTo("Timeout")));
		assertThat(Headers.TIMEOUT_SECOND, is(equalTo("Second-")));
		assertThat(Headers.TIMEOUT_INFINITE, is(equalTo("Infinite")));
	}
}
