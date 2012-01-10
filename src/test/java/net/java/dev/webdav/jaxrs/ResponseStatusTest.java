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

import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static net.java.dev.webdav.jaxrs.ResponseStatus.FAILED_DEPENDENCY;
import static net.java.dev.webdav.jaxrs.ResponseStatus.INSUFFICIENT_STORAGE;
import static net.java.dev.webdav.jaxrs.ResponseStatus.LOCKED;
import static net.java.dev.webdav.jaxrs.ResponseStatus.MULTI_STATUS;
import static net.java.dev.webdav.jaxrs.ResponseStatus.UNPROCESSABLE_ENTITY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests WebDAV response statuses.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ResponseStatusTest {
	@Test
	public final void test207MultiStatus() {
		assertThat(MULTI_STATUS.getStatusCode(), is(207));
		assertThat(MULTI_STATUS.getReasonPhrase(), is("Multi-Status"));
		assertThat(MULTI_STATUS.getFamily(), is(SUCCESSFUL));
	}

	@Test
	public final void test422UnprocessableEntity() {
		assertThat(UNPROCESSABLE_ENTITY.getStatusCode(), is(422));
		assertThat(UNPROCESSABLE_ENTITY.getReasonPhrase(), is("Unprocessable Entity"));
		assertThat(UNPROCESSABLE_ENTITY.getFamily(), is(CLIENT_ERROR));
	}

	@Test
	public final void test423Locked() {
		assertThat(LOCKED.getStatusCode(), is(423));
		assertThat(LOCKED.getReasonPhrase(), is("Locked"));
		assertThat(LOCKED.getFamily(), is(CLIENT_ERROR));
	}

	@Test
	public final void test424FailedDependency() {
		assertThat(FAILED_DEPENDENCY.getStatusCode(), is(424));
		assertThat(FAILED_DEPENDENCY.getReasonPhrase(), is("Failed Dependency"));
		assertThat(FAILED_DEPENDENCY.getFamily(), is(CLIENT_ERROR));
	}

	@Test
	public final void test507InsufficientStorage() {
		assertThat(INSUFFICIENT_STORAGE.getStatusCode(), is(507));
		assertThat(INSUFFICIENT_STORAGE.getReasonPhrase(), is("Insufficient Storage"));
		assertThat(INSUFFICIENT_STORAGE.getFamily(), is(SERVER_ERROR));
	}
}
