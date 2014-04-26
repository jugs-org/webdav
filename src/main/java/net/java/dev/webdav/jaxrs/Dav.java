package net.java.dev.webdav.jaxrs;

import static java.util.Arrays.asList;
import static net.java.dev.webdav.jaxrs.Headers.DAV_1;
import static net.java.dev.webdav.jaxrs.Headers.DAV_2;
import static net.java.dev.webdav.jaxrs.Headers.DAV_3;

import java.util.Collection;
import java.util.Iterator;

/**
 * WebDAV DAV Header
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#HEADER_DAV">Chapter 10.1 "DAV Header" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 * 
 * @since 2.0
 */
public final class Dav implements Iterable<String> {
	public static final Dav ONE = new Dav(DAV_1);

	public static final Dav ONE_TWO = new Dav(DAV_1, DAV_2);

	public static final Dav ONE_TWO_THREE = new Dav(DAV_1, DAV_2, DAV_3);

	public static final Dav ONE_THREE = new Dav(DAV_1, DAV_3);

	private final Collection<String> complianceClasses;

	Dav(final String... complianceClasses) {
		ensureClassesTwoAndThreeAlsoContainClassOne(asList(complianceClasses));
		this.complianceClasses = asList(complianceClasses);
	}

	private static final void ensureClassesTwoAndThreeAlsoContainClassOne(final Collection<String> complianceClasses) {
		if ((complianceClasses.contains(DAV_2) || complianceClasses.contains(DAV_3)) && !complianceClasses.contains(DAV_1))
			throw new IllegalArgumentException("DAV compliance classes 2 and 3 must only be used together with compliance class 1.");
	}

	@Override
	public final Iterator<String> iterator() {
		return this.complianceClasses.iterator();
	}

	public final boolean contains(final String complianceClass) {
		return this.complianceClasses.contains(complianceClass);
	}

	public static final Dav fromString(final String complianceClasses) {
		final String[] tokens = complianceClasses.replace(" ", "").split(",");
		final Dav dav = new Dav(tokens);
		if (dav.equals(ONE))
			return ONE;
		if (dav.equals(ONE_TWO))
			return ONE_TWO;
		if (dav.equals(ONE_TWO_THREE))
			return ONE_TWO_THREE;
		if (dav.equals(ONE_THREE))
			return ONE_THREE;
		return dav;
	}

	@Override
	public final String toString() {
		return asCommaSeparatedString(this.complianceClasses);
	}

	private static final String asCommaSeparatedString(final Collection<String> collection) {
		if (collection.isEmpty())
			return "";

		final StringBuilder stringBuilder = new StringBuilder();
		for (final String complianceClass : collection)
			stringBuilder.append(", ").append(complianceClass);
		return stringBuilder.substring(2);
	}

	@Override
	public final int hashCode() {
		return this.complianceClasses.hashCode();
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof Dav))
			return false;

		final Dav that = (Dav) other;

		return haveEqualItems(this.complianceClasses, that.complianceClasses);
	}

	/**
	 * @param someCollection
	 *            must not be {@code null}.
	 * @param anotherCollection
	 *            must not be {@code null}.
	 * @return {@code true} when both collections are of the same length and contain the same items, independent of ther order; {@code false} otherwise.
	 */
	private static final <T> boolean haveEqualItems(final Collection<T> someCollection, final Collection<T> anotherCollection) {
		return someCollection.size() == anotherCollection.size() && someCollection.containsAll(anotherCollection);
	}
}
