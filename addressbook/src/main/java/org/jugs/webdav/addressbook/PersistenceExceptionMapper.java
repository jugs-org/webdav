/*
 * Copyright 2008, 2009 Markus KARG
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

package org.jugs.webdav.addressbook;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static org.jugs.webdav.jaxrs.ResponseStatus.LOCKED;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Maps JPA exceptions to WebDAV Response Codes.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 */
@Provider
public final class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

	@Override
	public final Response toResponse(final PersistenceException e) {
		if (e instanceof NoResultException)
			return Response.status(NOT_FOUND).build();

		if (e instanceof OptimisticLockException)
			return Response.status(LOCKED.getStatusCode()).build();

		return Response.status(INTERNAL_SERVER_ERROR).build();
	}

}
