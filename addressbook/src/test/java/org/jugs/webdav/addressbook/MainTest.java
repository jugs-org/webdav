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
package org.jugs.webdav.addressbook;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import patterntesting.runtime.junit.NetworkTester;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-Test fuer {@link Main}.
 *
 * @author oboehm
 * @since 04.08.22
 */
class MainTest {

    private static final Logger log = LoggerFactory.getLogger(MainTest.class);
    private static final URI TEST_URI = URI.create("http://localhost:8002/addressbook");
    private static final Sardine SARDINE = SardineFactory.begin();

    @BeforeAll
    static void startFileServer() throws IOException {
        Main.main(new String[] {Integer.toString(TEST_URI.getPort())});
    }

    @Test
    public void pingPort() {
        NetworkTester.assertOnline(TEST_URI.getHost(), TEST_URI.getPort());
    }

    @Test
    public void testListRoot() throws IOException {
        checkWebDAV(TEST_URI);
    }

    public static void checkWebDAV(URI uri) throws IOException {
        List<DavResource> resources = SARDINE.list(uri.toString());
        assertFalse(resources.isEmpty());
        for (DavResource res : resources) {
            log.info("res = {}", res);
        }
    }

    @Test
    public void testLock() throws IOException {
        String s = SARDINE.lock(TEST_URI + "/test.adr");
        assertNotNull(s);
        log.info("s = '{}'", s);
    }

}