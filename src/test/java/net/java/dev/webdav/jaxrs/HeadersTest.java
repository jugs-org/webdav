/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2013 The java.net WebDAV Project
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

package net.java.dev.webdav.jaxrs;

import static net.java.dev.webdav.jaxrs.Headers.DAV;
import static net.java.dev.webdav.jaxrs.Headers.DEPTH;
import static net.java.dev.webdav.jaxrs.Headers.DEPTH_0;
import static net.java.dev.webdav.jaxrs.Headers.DEPTH_1;
import static net.java.dev.webdav.jaxrs.Headers.DEPTH_INFINITY;
import static net.java.dev.webdav.jaxrs.Headers.DESTINATION;
import static net.java.dev.webdav.jaxrs.Headers.IF;
import static net.java.dev.webdav.jaxrs.Headers.LOCK_TOKEN;
import static net.java.dev.webdav.jaxrs.Headers.OVERWRITE;
import static net.java.dev.webdav.jaxrs.Headers.OVERWRITE_FALSE;
import static net.java.dev.webdav.jaxrs.Headers.OVERWRITE_TRUE;
import static net.java.dev.webdav.jaxrs.Headers.TIMEOUT;
import static net.java.dev.webdav.jaxrs.Headers.TIMEOUT_INFINITE;
import static net.java.dev.webdav.jaxrs.Headers.TIMEOUT_SECOND;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests WebDAV headers.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class HeadersTest {
	@Test
	public final void webDAVConstantsProduceCorrectly() {
		assertThat(DAV, is("DAV"));
		assertThat(DEPTH, is("Depth"));
		assertThat(DEPTH_0, is("0"));
		assertThat(DEPTH_1, is("1"));
		assertThat(DEPTH_INFINITY, is("infinity"));
		assertThat(DESTINATION, is("Destination"));
		assertThat(IF, is("If"));
		assertThat(LOCK_TOKEN, is("Lock-Token"));
		assertThat(OVERWRITE, is("Overwrite"));
		assertThat(OVERWRITE_TRUE, is("T"));
		assertThat(OVERWRITE_FALSE, is("F"));
		assertThat(TIMEOUT, is("Timeout"));
		assertThat(TIMEOUT_SECOND, is("Second-"));
		assertThat(TIMEOUT_INFINITE, is("Infinite"));
	}
}
