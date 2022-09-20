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
package org.jugs.webdav.interop;

import org.junit.jupiter.api.Test;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.CharArrayWriter;

/**
 * This test class has the same functionality as old XslTransformer class but
 * as unit test.
 *
 * @author oboehm
 * @since 10.06.22
 */
public class XslTransformerTest {

    @Test
    public void testTransform() throws TransformerException {
        String styleSheet = "src/main/resources/xml/prefix.xsl";
        String webdavResponse = "src/test/resources/xml/webdav.xml";

        Source styleSource = new StreamSource(styleSheet);
        Source webdavSource = new StreamSource(webdavResponse);

        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory
                .newTransformer(styleSource);
        CharArrayWriter caw = new CharArrayWriter();
        StreamResult result = new StreamResult(caw);
        transformer.transform(webdavSource, result);

        System.out.println(caw);
    }

}