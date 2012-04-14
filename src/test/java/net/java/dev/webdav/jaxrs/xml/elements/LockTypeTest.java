/*
 * Copyright 2012 Markus KARG
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

package net.java.dev.webdav.jaxrs.xml.elements;

import static net.java.dev.webdav.jaxrs.xml.elements.LockType.WRITE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;

import org.junit.Test;

/**
 * Unit test for {@link LockType}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockTypeTest {
	@Test
	public final void marshalling() {
		final Writer writer = new StringWriter();
		JAXB.marshal(WRITE, writer);
		final String actual = writer.toString();
		final String expected = "<D:locktype xmlns:D=\"DAV:\"><D:write/></D:locktype>";
		assertThat(the(actual), isEquivalentTo(the(expected)));
	}

	@Test
	public final void unmarshalling() {
		final Reader reader = new StringReader("<D:locktype xmlns:D=\"DAV:\"><D:write/></D:locktype>");
		final LockType actual = JAXB.unmarshal(reader, LockType.class);
		final LockType expected = WRITE;
		assertThat(actual, is(equalTo(expected)));
	}
}
