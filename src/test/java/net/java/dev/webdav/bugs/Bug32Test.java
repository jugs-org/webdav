package net.java.dev.webdav.bugs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import net.java.dev.webdav.jaxrs.xml.elements.Rfc3339DateTimeFormat;
import net.java.dev.webdav.jaxrs.xml.properties.CreationDate;
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
	public final void shouldSetNullWhenProvidedEmptyString() throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// given
		final CreationDate creationDate = Utilities.buildInstanceOf(CreationDate.class);

		// when
		UnitTestUtilities.invoke(creationDate, "setXmlDateTime", "");

		// then
		assertThat(creationDate.getDateTime(), is(nullValue()));
	}
}
