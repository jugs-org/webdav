/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.jugs.webdav.bugs;

import org.jugs.webdav.jaxrs.xml.elements.Rfc3339DateTimeFormat;
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLength;
import org.jugs.webdav.jaxrs.xml.properties.GetLastModified;
import org.jugs.webdav.util.UnitTestUtilities;
import org.jugs.webdav.util.Utilities;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Detects recurrence of bug <a href="https://java.net/jira/browse/WEBDAV_JAXRS-32">#32</a>.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class Bug32Test {
	/**
	 * The parser incorrectly threw unchecked {@link RuntimeException}, which JAXB cannot handle. Instead, a declared {@link ParseException} is to be thrown,
	 * which JAXB can handle.
	 */
	@Test
	void shouldThrowParseExceptionForEmptyString() throws ParseException {
		final Rfc3339DateTimeFormat parser = new Rfc3339DateTimeFormat();
		assertThrows(ParseException.class, () -> parser.parse(""));
	}

	/**
	 * JAXB sometimes provides an empty string instead of null when unmarshalling empty elements. In such case,
	 * the setXmlValue method in {@link CreationDate} did not detect
	 * the empty string, but forwarded it to the parser, leading to an exception. Actually no exception must happen instead, but the case must simply treat this
	 * like {@code null}.
	 */
	@Test
	void shouldSetNullWhenProvidedEmptyValueToCreationDate() throws ParseException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// given
		final CreationDate creationDate = Utilities.buildInstanceOf(CreationDate.class);

		// when
		UnitTestUtilities.invoke(creationDate, "setXmlValue", "");

		// then
		assertThat(creationDate.getDateTime(), is(nullValue()));
	}

	/**
	 * JAXB sometimes provides an empty string instead of null when unmarshalling empty elements. In such case,
	 * the setXmlValue method in {@link GetContentLength} did not
	 * detect the empty string, but forwarded it to the {@link Long} parser, leading to an exception internally of JAXB. Actually no exception must happen
	 * instead, but the case must simply treat this like {@code null}.
	 */
	@Test
	void shouldSetNullWhenProvidedEmptyValueToGetContentLength() throws ParseException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// given
		final GetContentLength getContentLength = Utilities.buildInstanceOf(GetContentLength.class);

		// when
		UnitTestUtilities.invoke(getContentLength, "setXmlValue", "");

		// then
		assertThat(getContentLength.getContentLength(), is(0L));
	}

	/**
	 * JAXB sometimes provides an empty string instead of null when unmarshalling empty elements. In such case,
	 * the setXmlValue method in {@link CreationDate} did not detect
	 * the empty string, but forwarded it to the parser, leading to an exception. Actually no exception must happen instead, but the case must simply treat this
	 * like {@code null}.
	 */
	@Test
	void shouldSetNullWhenProvidedEmptyValueToGetLastModified() throws ParseException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// given
		final GetLastModified getLastModified = Utilities.buildInstanceOf(GetLastModified.class);

		// when
		UnitTestUtilities.invoke(getLastModified, "setXmlValue", "");

		// then
		assertThat(getLastModified.getDateTime(), is(nullValue()));
	}
}
