/*
 * Copyright (c) 2022 by Oli B.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express orimplied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 02.09.22 by oboehm
 */
package org.jugs.webdav.fileserver.resources;

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit-Test fuer {@link FileServerResource} ...
 *
 * @author oboehm
 * @since 02.09.22
 */
class FileServerResourceTest {

    private final FileServerResource resource = new FileServerResource();

    @Test
    void options() {
        Response response = resource.options();
        assertNotNull(response);
        MultivaluedMap<String, Object> headers = response.getMetadata();
        assertContains(headers, "Allow", "LOCK");
    }

    private void assertContains(MultivaluedMap<String, Object> headers, String key, String expected) {
        Object value = headers.get(key);
        assertNotNull(value);
        assertThat(value.toString(), containsString(expected));
    }

}