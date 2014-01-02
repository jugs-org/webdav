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

package net.java.dev.webdav.jaxrs.xml.elements;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link PropertyUpdate}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class PropertyUpdateTest extends AbstractJaxbCoreFunctionality<PropertyUpdate> {
	private static final Prop PROP = new Prop();
	private static final Remove REMOVE = new Remove(PROP);
	private static final Set SET = new Set(PROP);

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNull() {
		new PropertyUpdate((RemoveOrSet) null);
	}

	@DataPoint
	public static final Object[] SINGLE_UPDATE = { new PropertyUpdate(REMOVE),
			"<D:propertyupdate xmlns:D=\"DAV:\"><D:remove><D:prop/></D:remove></D:propertyupdate>", asList(REMOVE) };

	@DataPoint
	public static final Object[] MULTIPLE_UPDATES = { new PropertyUpdate(SET, REMOVE, SET),
			"<D:propertyupdate xmlns:D=\"DAV:\"><D:set><D:prop/></D:set><D:remove><D:prop/></D:remove><D:set><D:prop/></D:set></D:propertyupdate>",
			asList(SET, REMOVE, SET) };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final PropertyUpdate actual, final PropertyUpdate expected, final Object[] dataPoint) {
		assertThat(actual.list(), is(dataPoint[2]));
		assertThat(expected.list(), is(dataPoint[2]));
	}

	@Override
	protected final PropertyUpdate getInstance() {
		return new PropertyUpdate(SET);
	}
}
