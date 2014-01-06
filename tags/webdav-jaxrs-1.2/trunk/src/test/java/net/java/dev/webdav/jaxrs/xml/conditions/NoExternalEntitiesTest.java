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

package net.java.dev.webdav.jaxrs.xml.conditions;

import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link NoExternalEntities}
 * 
 * @author Markus KARG (mkarg@java.net) *
 */
public final class NoExternalEntitiesTest extends AbstractJaxbCoreFunctionality<NoExternalEntities> {
	@DataPoint
	public static final Object[] SINGLETON = new Object[] { NoExternalEntities.NO_EXTERNAL_ENTITIES, "<D:no-external-entities xmlns:D=\"DAV:\"/>" };

	@Override
	protected final NoExternalEntities getSingleton() {
		return NoExternalEntities.NO_EXTERNAL_ENTITIES;
	}
	@Override
	protected final String getString() {
		return "NoExternalEntities[]";
	}
}
