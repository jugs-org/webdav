package net.java.dev.webdav.jaxrs;

import net.java.dev.webdav.jaxrs.xml.conditions.NoConflictingLock;
import net.java.dev.webdav.jaxrs.xml.elements.HRef;

import org.junit.Test;

public final class SpeedTest {
	@Test
	public final void speed() {
		for (int i = 0; i < 1000000; i++) {
			int r = 0;
			final NoConflictingLock o = new NoConflictingLock(new HRef("A"), new HRef("B"), new HRef("C"));
			for (int j = 0; j < 3; j++)
				for (final HRef hRef : o.getHRefs())
					r++;
		}
	}
}
