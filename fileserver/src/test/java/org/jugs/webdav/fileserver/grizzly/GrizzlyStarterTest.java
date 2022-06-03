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
package org.jugs.webdav.fileserver.grizzly;

import org.jugs.webdav.fileserver.FileServerStarterTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import patterntesting.runtime.junit.NetworkTester;

import java.io.IOException;
import java.net.URI;

/**
 * Unit-Test fuer {@link GrizzlyStarter} ...
 *
 * @author oboehm
 * @since 03.06.22
 */
class GrizzlyStarterTest {

    private static final URI webdavURI = URI.create("http://localhost:8000/fileserver");

    @BeforeAll
    static void startGrizzlyServer() throws IOException {
        GrizzlyStarter.start(webdavURI);
    }

    @Test
    public void testOnline() {
        NetworkTester.assertOnline(webdavURI.getHost(), webdavURI.getPort());
    }

    @Test
    public void testListRoot() throws IOException {
        FileServerStarterTest.checkWebDAV(webdavURI);
    }

}