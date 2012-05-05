package net.java.dev.webdav.util;


/**
 * Common purpose utilities.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class Utilities {

	/**
	 * Prevents {@link NullPointerException} when checking two references for equality where the first reference can be {@code null}.
	 * 
	 * @param firstObject
	 *            If this is not {@code null}, its {@link #equals(Object)} method will get called.
	 * @param secondObject
	 *            This will be the sole parameter provided to the first reference's {@link #equals(Object)}.
	 * @return {@code true} if, and only if, either both references are null or referencing equal objects.
	 */
	public static final boolean sameOrEqual(final Object firstObject, final Object secondObject) {
		return firstObject == secondObject || firstObject != null && firstObject.equals(secondObject);
	}
}
