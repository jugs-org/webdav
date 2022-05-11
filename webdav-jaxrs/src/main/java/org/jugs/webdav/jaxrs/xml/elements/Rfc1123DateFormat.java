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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * RFC 1123 date format<br>
 * 
 * This class formats and parses dates using the RFC 1123 compliant pattern [WDY], [DY] [MTH] [YEAR] [hh]:[mm]:[ss] GMT.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@SuppressWarnings("serial")
public final class Rfc1123DateFormat extends SimpleDateFormat {

	public Rfc1123DateFormat() {
		super("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
		this.calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	}

}
