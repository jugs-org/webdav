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

import static net.java.dev.webdav.jaxrs.xml.elements.Depth.INFINITY;
import static net.java.dev.webdav.jaxrs.xml.elements.Depth.ONE;
import static net.java.dev.webdav.jaxrs.xml.elements.Depth.ZERO;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.experimental.theories.DataPoints;

/**
 * Unit test for {@link Depth}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class DepthTest extends AbstractJaxbCoreFunctionality<Depth> {
	@DataPoints
	public static final Object[][] DATA_POINTS = { { ZERO, "<D:depth xmlns:D=\"DAV:\">0</D:depth>" }, { ONE, "<D:depth xmlns:D=\"DAV:\">1</D:depth>" },
			{ INFINITY, "<D:depth xmlns:D=\"DAV:\">infinity</D:depth>" } };
}
