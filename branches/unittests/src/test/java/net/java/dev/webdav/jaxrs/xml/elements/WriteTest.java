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

import static net.java.dev.webdav.jaxrs.xml.elements.Write.SINGLETON;

import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link Write}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class WriteTest extends AbstractElementTest<Write> {
	@DataPoint
	public static final Object[] DATA_POINT = { SINGLETON, "<D:write xmlns:D=\"DAV:\"/>" };
}
