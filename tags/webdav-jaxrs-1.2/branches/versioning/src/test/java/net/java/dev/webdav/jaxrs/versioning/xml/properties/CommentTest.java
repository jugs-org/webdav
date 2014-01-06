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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;

import org.junit.Test;

/**
 * Unit test for Versioning Extensions to WebDAV <code>comment</code> Property.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 */
public final class CommentTest {

	@Test
	public final void testMarshal() {
		final Writer xml = new StringWriter();

		JAXB.marshal(new Comment(), xml);
		assertTrue("Comment() must marshal as empty <comment>.", xml.toString().contains("<comment xmlns=\"DAV:\"/>"));

		JAXB.marshal(new Comment("abc"), xml);
		assertTrue("Comment(String) must marshal as <comment>String</comment>.", xml.toString().contains("<comment xmlns=\"DAV:\">abc</comment>"));
	}

	@Test
	public final void testUnmarshal() {
		assertTrue("Empty <comment> comment ", JAXB.unmarshal(new StringReader("<comment xmlns=\"DAV:\"/>"), Comment.class).getComment().isEmpty());
		assertEquals(JAXB.unmarshal(new StringReader("<comment xmlns=\"DAV:\">abc</comment>"), Comment.class).getComment(), "abc");
	}

}
