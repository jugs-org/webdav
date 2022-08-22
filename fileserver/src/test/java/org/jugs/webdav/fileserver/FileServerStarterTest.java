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
package org.jugs.webdav.fileserver;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import patterntesting.runtime.junit.NetworkTester;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-Test fuer {@link FileServerStarter} ...
 *
 * @author oboehm
 * @since 02.06.22
 */
public class FileServerStarterTest {

    private static final Logger log = Logger.getLogger(FileServerStarterTest.class.getName());
    public static final URI TEST_URI = URI.create("http://localhost:8001/fileserver");
    private static final int TEST_PORT = TEST_URI.getPort();

    @BeforeAll
    static void startFileServer() throws IOException {
        FileServerStarter.main(new String[]{Integer.toString(TEST_PORT)});
    }

    @Test
    public void pingPort() {
        NetworkTester.assertOnline(TEST_URI.getHost(), TEST_PORT);
    }

    @Test
    public void testListRoot() throws IOException {
        checkWebDAV(TEST_URI);
    }

    public static void checkWebDAV(URI uri) throws IOException {
        Sardine sardine = SardineFactory.begin();
        List<DavResource> resources = sardine.list(uri.toString());
        assertFalse(resources.isEmpty());
        for (DavResource res : resources) {
            log.info("res = " + res);
        }
    }

}
