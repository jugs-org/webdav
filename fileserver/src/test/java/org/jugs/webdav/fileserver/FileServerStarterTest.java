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
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpResponseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterntesting.runtime.junit.NetworkTester;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-Test fuer {@link FileServerStarter} ...
 *
 * @author oboehm
 * @since 02.06.22
 */
public class FileServerStarterTest {

    private static final Logger log = LoggerFactory.getLogger(FileServerStarterTest.class.getName());
    public static final URI TEST_URI = URI.create("http://localhost:8001/fileserver");
    private static final int TEST_PORT = TEST_URI.getPort();
    private static final Sardine SARDINE = SardineFactory.begin();

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
        List<DavResource> resources = SARDINE.list(uri.toString());
        assertFalse(resources.isEmpty());
        for (DavResource res : resources) {
            log.info("res = " + res);
        }
    }

    @Test
    public void testLockUnlock() throws IOException {
        String testToken = SARDINE.lock(TEST_URI + "/hello.world");
        log.info("testToken = \"{}\"", testToken);
        SARDINE.unlock(TEST_URI + "/hello.world", testToken);
    }

    @Test
    public void testPropfind() throws IOException {
        List<DavResource> resources = SARDINE.propfind(TEST_URI.toString(), 0, new HashSet<>());
        assertFalse(resources.isEmpty());
    }

    @Test
    public void testGet() throws IOException {
        List<DavResource> resources = SARDINE.propfind(TEST_URI + "/", 1, new HashSet<>());
        assertFalse(resources.isEmpty());
        for (DavResource rsc : resources) {
            if (rsc.getContentType().contains("dir")) {
                continue;
            }
            try (InputStream istream = SARDINE.get(rsc.getHref().toString())) {
                assertNotNull(istream);
                byte[] data = IOUtils.toByteArray(istream);
                log.info("{} bytes read from {}.", data.length, rsc);
                assertEquals(rsc.getContentLength(), data.length);
            }
        }
    }

    @Test
    public void testPropfind404() throws IOException {
        try {
            SARDINE.propfind(TEST_URI + "/nirwana", 0, new HashSet<>());
            fail("Exception expected.");
        } catch (HttpResponseException expected) {
            assertEquals(404, expected.getStatusCode());
        }
    }

    //@Test
    public void testPut() throws IOException {
        URI resource = URI.create(TEST_URI + "/test-put.txt");
        byte[] content = "put, put, put, gaaack".getBytes(StandardCharsets.UTF_8);
        SARDINE.put(resource.toString(), content, MediaType.TEXT_XML);
    }

}
