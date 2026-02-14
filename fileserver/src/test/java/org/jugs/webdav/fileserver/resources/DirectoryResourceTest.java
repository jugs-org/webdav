/*
 * Copyright (c) 2026 by Oli B.
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
package org.jugs.webdav.fileserver.resources;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DirectoryResource}.
 *
 * @author oboehm
 * @since 13.02.26
 */
class DirectoryResourceTest {

    private final DirectoryResource resource = new DirectoryResource(new File("./target/test"), "./target/test");

    /**
     * Unit test for issue #2.
     *
     * @throws URISyntaxException in case of wrong URIN
     */
    @Test
    void move() throws URISyntaxException {
        // GIVEN
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getBaseUri()).thenReturn(URI.create("http://localhost:8002"));
        // WHEN
        Response response = resource.move(uriInfo, null, "http://localhost:8002/fileserver/target/hello");
        // THEN
        assertNotNull(response);
        assertNotEquals(500, response.getStatus());
    }

}