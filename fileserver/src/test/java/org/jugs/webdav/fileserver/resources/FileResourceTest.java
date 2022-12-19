/*
 * Copyright (c) 2022 by Oli B.
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

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Unit-Test fuer {@link FileResource} ...
 *
 * @author oboehm
 * @since 25.11.22
 */
class FileResourceTest {

    @Test
    void fileNotFound() {
        FileResource resource = new FileResource(new File("./nir/wa/na"), "/nir/wa/na");
        UriInfo info = mock(UriInfo.class);
        Response response = resource.get(info);
        assertEquals(404, response.getStatus());
    }

}