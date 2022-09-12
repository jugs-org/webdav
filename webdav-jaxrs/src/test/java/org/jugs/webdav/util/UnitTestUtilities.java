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

package org.jugs.webdav.util;

import org.hamcrest.CoreMatchers;
import org.jugs.webdav.jaxrs.ImmutableDate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Utilities for unit tests.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class UnitTestUtilities {

	/**
	 * Asserts that a method's {@link Date} getter in fact returns immutable objects, or a different object with every call.
	 * 
	 * @param immutableObject
	 *            The object to check.
	 * @param getterName
	 *            The getter's name to check.
	 * @throws SecurityException
	 *             When the getter is not allowed to be called.
	 * @throws NoSuchMethodException
	 *             When no such getter exists.
	 * @throws InvocationTargetException
	 *             When the getter cannot get called.
	 * @throws IllegalArgumentException
	 *             When the getter needs arguments.
	 * @throws IllegalAccessException
	 *             When the getter is not public.
	 */
	public static final <T> void assertEffectivelyImmutableDate(final T immutableObject, final String getterName) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Method getter = immutableObject.getClass().getMethod(getterName);
		final Date resultOfFirstCall = (Date) getter.invoke(immutableObject);
		final Date resultOfSecondCall = (Date) getter.invoke(immutableObject);
		assertThat(resultOfFirstCall, is(
                CoreMatchers.anyOf(ImmutableDate.immutable(), not(sameInstance(resultOfSecondCall)), nullValue())));
	}

	/**
	 * Allows to invoke {@code private} methods.
	 * 
	 * @param object
	 *            The method will be invoked on this instance.
	 * @param methodName
	 *            The name of the method to invoke.
	 * @param parameter
	 *            The value to pass into the method.
	 * @throws SecurityException
	 *             If a security manager, <i>s</i>, is present and any of the following conditions is met:
	 *             <ul>
	 *             <li>invocation of {@link SecurityManager}'s check method denies access to the declared method
	 *             <li>the caller's class loader is not the same as or an ancestor of the class loader for the current class and invocation of
	 *             {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of this class
	 *             </ul>
	 * @throws NoSuchMethodException
	 *             if a matching method is not found.
	 * @throws InvocationTargetException
	 *             if the underlying method throws an exception.
	 * @throws IllegalArgumentException
	 *             if the method is an instance method and the specified object argument is not an instance of the class or interface declaring the underlying
	 *             method (or of a subclass or implementor thereof); if the number of actual and formal parameters differ; if an unwrapping conversion for
	 *             primitive arguments fails; or if, after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type
	 *             by a method invocation conversion.
	 * @throws IllegalAccessException
	 *             if this {@code Method} object is enforcing Java language access control and the underlying method is inaccessible.
	 */
	public static final <T> void invoke(final T object, final String methodName, final Object parameter) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Method method = object.getClass().getDeclaredMethod(methodName, parameter.getClass());
		method.setAccessible(true);
		method.invoke(object, parameter);
	}
}
