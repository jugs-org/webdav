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
 * (c)reated 19.08.22 by oboehm
 */
package org.jugs.webdav.addressbook;

import org.jugs.webdav.jaxrs.xml.elements.MultiStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.UriInfo;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit-Test fuer {@link AddressBook} ...
 *
 * @author oboehm
 * @since 19.08.22
 */
class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    void propfindRoot() {
        checkProfind("0");
    }

    @Test
    void propfind() {
        checkProfind("1");
    }

    private void checkProfind(String depth) {
        UriInfo info = mock(UriInfo.class);
        when(info.getRequestUri()).thenReturn(URI.create("http://localhost/addressbook"));
        MultiStatus status = addressBook.propfind(info, depth);
        assertNotNull(status);
    }

}