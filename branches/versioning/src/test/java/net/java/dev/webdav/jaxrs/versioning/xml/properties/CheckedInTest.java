/*
 * Copyright 2010 Markus KARG
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

package net.java.dev.webdav.jaxrs.versioning.xml.properties;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;

import net.java.dev.webdav.jaxrs.xml.elements.HRef;

import org.junit.Test;

/**
 * Unit test for Versioning Extensions to WebDAV <code>checked-in</code>
 * Property.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 */
public final class CheckedInTest {

	@Test
	public final void testMarshal() {
		final Writer xml = new StringWriter();

		JAXB.marshal(new CheckedIn(), xml);
		assertTrue("CheckedIn() must marshal as empty <checked-in>.", xml.toString().contains("<checked-in xmlns=\"DAV:\"/>"));

		JAXB.marshal(new CheckedIn(new HRef("abc")), xml);
		assertTrue("CheckedIn(HRef) must marshal as <checked-in><href>.", xml.toString().replaceAll("[\r\n]", "").matches(".*<checked-in xmlns=\"DAV:\">.*<href>abc</href>.*</checked-in>"));
	}

	@Test
	public final void testUnmarshal() {
		assertThat("Empty <checked-in> must unmarshal with href set to null.", JAXB.unmarshal(new StringReader("<checked-in xmlns=\"DAV:\"/>"), CheckedIn.class).getHRef(), nullValue());
		assertThat("<checked-in><href> must unmarshal with hRef set to HRef instance.", JAXB.unmarshal(new StringReader("<checked-in xmlns=\"DAV:\"><href>abc</href></checked-in>"), CheckedIn.class).getHRef(), is(HRef.class));
	}

}
