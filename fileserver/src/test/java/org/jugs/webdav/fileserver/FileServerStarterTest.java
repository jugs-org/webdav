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
import com.github.sardine.impl.methods.HttpPropFind;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.placeholder.PlaceholderDifferenceEvaluator;
import patterntesting.runtime.junit.NetworkTester;

import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.File;
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
        FileServerStarter.start(new String[]{Integer.toString(TEST_PORT)});
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
    public void testPropFindDepth1() throws IOException {
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

    @Test
    public void testGetMethod() throws IOException {
        get(TEST_URI);
    }

    @Test
    public void testGetWithBackslash() throws IOException {
        get(URI.create(TEST_URI + "/"));
    }

    private static void get(URI uri) throws IOException {
        HttpResponse response = getHttpResponse(new HttpGet(uri));
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        Header[] contentType = response.getHeaders("Content-Type");
        assertEquals(MediaType.TEXT_HTML, contentType[0].getValue());
    }

    @Test
    public void testPropfindMethod() throws IOException {
        HttpResponse response = getHttpResponse(new HttpPropFind(TEST_URI));
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testPropfindInterop() throws IOException {
        File testDir = new File("target", "test");
        FileUtils.deleteDirectory(testDir);
        if (testDir.mkdirs()) {
            log.info("Dir '{}' was (re)created.", testDir);
        }
        HttpResponse response = getHttpResponse(new HttpPropFind(TEST_URI + "/" + testDir));
        String xml = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        assertSimilarXml(new File("src/test/resources/response/profind-target-test.xml"), xml);
    }

    private static void assertSimilarXml(File ref, String xml) throws IOException {
        String controlXml = FileUtils.readFileToString(ref, StandardCharsets.UTF_8);
        Diff diff = DiffBuilder.compare(controlXml).withTest(xml)
                .ignoreWhitespace()
                .withDifferenceEvaluator(new PlaceholderDifferenceEvaluator())
                .checkForSimilar().build();
        for (Difference d : diff.getDifferences()) {
            log.info("diff: {}", d);
        }
        assertFalse(diff.hasDifferences(), "diff: " + diff);
    }

    @Test
    public void testOptionInterop() throws IOException {
        HttpResponse response = getHttpResponse(new HttpOptions(TEST_URI));
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);
        Header[] header = response.getHeaders("MS-Author-Via");
        assertEquals(1, header.length);
        assertEquals("DAV", header[0].getValue());
    }

    private static HttpResponse getHttpResponse(HttpUriRequest method) throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpResponse response = client.execute(method);
            HttpEntity entity = response.getEntity();
            log.info("Body of {} is {}.", method, entity);
            byte[] content = IOUtils.toByteArray(entity.getContent());
            if (log.isDebugEnabled()) {
                log.debug(new String(content, StandardCharsets.UTF_8));
            }
            return wrapResponse(response, content);
        }
    }

    private static HttpResponse wrapResponse(HttpResponse response, byte[] content) {
        BasicHttpEntity filled = new BasicHttpEntity();
        filled.setContent(new ByteArrayInputStream(content));
        response.setEntity(filled);
        return response;
    }

    @AfterAll
    static void stopFileServer() {
        FileServerStarter.stop();
    }

}
