/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2014 The java.net WebDAV Project
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
package net.java.dev.webdav.bugs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import net.java.dev.webdav.jaxrs.xml.elements.Rfc3339DateTimeFormat;
import net.java.dev.webdav.jaxrs.xml.properties.CreationDate;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentLength;
import net.java.dev.webdav.jaxrs.xml.properties.GetLastModified;
import net.java.dev.webdav.util.UnitTestUtilities;
import net.java.dev.webdav.util.Utilities;

import org.junit.Test;

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
	@Test(expected = ParseException.class)
	public final void shouldThrowParseExceptionForEmptyString() throws ParseException {
		// given
		final Rfc3339DateTimeFormat parser = new Rfc3339DateTimeFormat();

		// when
		parser.parse("");

		// then throws checked ParseException
	}

	/**
	 * JAXB sometimes provides an empty string instead of null when unmarshalling empty elements. In such case, {@link CreationDate#setXmlDateTime} did not
	 * detect the empty string, but forwarded it to the parser, leading to an exception. Actually no exception must happen instead, but the case must simply
	 * treat this like {@code null}.
	 */
	@Test
	public final void shouldSetNullWhenProvidedEmptyValueToCreationDate() throws ParseException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// given
		final CreationDate creationDate = Utilities.buildInstanceOf(CreationDate.class);

		// when
		UnitTestUtilities.invoke(creationDate, "setXmlValue", "");

		// then
		assertThat(creationDate.getDateTime(), is(nullValue()));
	}

	/**
	 * JAXB sometimes provides an empty string instead of null when unmarshalling empty elements. In such case, {@link GetContentLength#setXmlDateTime} did not
	 * detect the empty string, but forwarded it to the {@link Long} parser, leading to an exception internally of JAXB. Actually no exception must happen
	 * instead, but the case must simply treat this like {@code null}.
	 */
	@Test
	public final void shouldSetNullWhenProvidedEmptyValueToGetContentLength() throws ParseException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// given
		final GetContentLength getContentLength = Utilities.buildInstanceOf(GetContentLength.class);

		// when
		UnitTestUtilities.invoke(getContentLength, "setXmlValue", "");

		// then
		assertThat(getContentLength.getContentLength(), is(0L));
	}

	/**
	 * JAXB sometimes provides an empty string instead of null when unmarshalling empty elements. In such case, {@link CreationDate#setXmlDateTime} did not
	 * detect the empty string, but forwarded it to the parser, leading to an exception. Actually no exception must happen instead, but the case must simply
	 * treat this like {@code null}.
	 */
	@Test
	public final void shouldSetNullWhenProvidedEmptyValueToGetLastModified() throws ParseException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// given
		final GetLastModified getLastModified = Utilities.buildInstanceOf(GetLastModified.class);

		// when
		UnitTestUtilities.invoke(getLastModified, "setXmlValue", "");

		// then
		assertThat(getLastModified.getDateTime(), is(nullValue()));
	}
}
