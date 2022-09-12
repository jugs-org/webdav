/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2022 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs.xml.elements;

import static org.jugs.webdav.jaxrs.xml.elements.Depth.INFINITY;
import static org.jugs.webdav.jaxrs.xml.elements.Depth.ONE;
import static org.jugs.webdav.jaxrs.xml.elements.Depth.ZERO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;

/**
 * Unit test for {@link Depth}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class DepthTest extends AbstractJaxbCoreFunctionality<Depth> {
	@DataPoints
	public static final Object[][] DATA_POINTS = { { ZERO, "<D:depth xmlns:D=\"DAV:\">0</D:depth>", "0" },
			{ ONE, "<D:depth xmlns:D=\"DAV:\">1</D:depth>", "1" }, { INFINITY, "<D:depth xmlns:D=\"DAV:\">infinity</D:depth>", "infinity" } };

	@Override
	protected final Depth getInstance() {
		return Depth.INFINITY;
	}

	@Override
	protected final String getString() {
		return "INFINITY";
	}

	@Theory
	public final void shouldParseEnum(final Object[] dataPoint) {
		// given
		final String depthHeaderValue = (String) dataPoint[2];

		// when
		final Depth depth = Depth.fromString(depthHeaderValue);

		// then
		final Depth expectedDepth = (Depth) dataPoint[0];
		assertThat(depth, is(sameInstance(expectedDepth)));
	}
}
