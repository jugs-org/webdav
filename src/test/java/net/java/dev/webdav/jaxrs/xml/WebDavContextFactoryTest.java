package net.java.dev.webdav.jaxrs.xml;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import net.java.dev.webdav.jaxrs.xml.conditions.CannotModifyProtectedProperty;
import net.java.dev.webdav.jaxrs.xml.conditions.LockTokenMatchesRequestUri;
import net.java.dev.webdav.jaxrs.xml.conditions.LockTokenSubmitted;
import net.java.dev.webdav.jaxrs.xml.conditions.NoConflictingLock;
import net.java.dev.webdav.jaxrs.xml.conditions.NoExternalEntities;
import net.java.dev.webdav.jaxrs.xml.conditions.PreservedLiveProperties;
import net.java.dev.webdav.jaxrs.xml.conditions.PropFindFiniteDepth;
import net.java.dev.webdav.jaxrs.xml.elements.ActiveLock;
import net.java.dev.webdav.jaxrs.xml.elements.AllProp;
import net.java.dev.webdav.jaxrs.xml.elements.Collection;
import net.java.dev.webdav.jaxrs.xml.elements.Depth;
import net.java.dev.webdav.jaxrs.xml.elements.Error;
import net.java.dev.webdav.jaxrs.xml.elements.Exclusive;
import net.java.dev.webdav.jaxrs.xml.elements.HRef;
import net.java.dev.webdav.jaxrs.xml.elements.Include;
import net.java.dev.webdav.jaxrs.xml.elements.Location;
import net.java.dev.webdav.jaxrs.xml.elements.LockEntry;
import net.java.dev.webdav.jaxrs.xml.elements.LockInfo;
import net.java.dev.webdav.jaxrs.xml.elements.LockRoot;
import net.java.dev.webdav.jaxrs.xml.elements.LockScope;
import net.java.dev.webdav.jaxrs.xml.elements.LockToken;
import net.java.dev.webdav.jaxrs.xml.elements.LockType;
import net.java.dev.webdav.jaxrs.xml.elements.MultiStatus;
import net.java.dev.webdav.jaxrs.xml.elements.Owner;
import net.java.dev.webdav.jaxrs.xml.elements.Prop;
import net.java.dev.webdav.jaxrs.xml.elements.PropFind;
import net.java.dev.webdav.jaxrs.xml.elements.PropName;
import net.java.dev.webdav.jaxrs.xml.elements.PropStat;
import net.java.dev.webdav.jaxrs.xml.elements.PropertyUpdate;
import net.java.dev.webdav.jaxrs.xml.elements.Remove;
import net.java.dev.webdav.jaxrs.xml.elements.Response;
import net.java.dev.webdav.jaxrs.xml.elements.ResponseDescription;
import net.java.dev.webdav.jaxrs.xml.elements.Set;
import net.java.dev.webdav.jaxrs.xml.elements.Shared;
import net.java.dev.webdav.jaxrs.xml.elements.Status;
import net.java.dev.webdav.jaxrs.xml.elements.TimeOut;
import net.java.dev.webdav.jaxrs.xml.elements.Write;
import net.java.dev.webdav.jaxrs.xml.properties.CreationDate;
import net.java.dev.webdav.jaxrs.xml.properties.DisplayName;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentLanguage;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentLength;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentType;
import net.java.dev.webdav.jaxrs.xml.properties.GetETag;
import net.java.dev.webdav.jaxrs.xml.properties.GetLastModified;
import net.java.dev.webdav.jaxrs.xml.properties.LockDiscovery;
import net.java.dev.webdav.jaxrs.xml.properties.ResourceType;
import net.java.dev.webdav.jaxrs.xml.properties.SupportedLock;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public final class WebDavContextFactoryTest {
	@DataPoints
	public static final Class<?>[] DATA_POINTS = new Class<?>[] { ActiveLock.class, AllProp.class, CannotModifyProtectedProperty.class, Collection.class,
			CreationDate.class, Depth.class, DisplayName.class, Error.class, Exclusive.class, GetContentLanguage.class, GetContentLength.class,
			GetContentType.class, GetETag.class, GetLastModified.class, HRef.class, Include.class, Location.class, LockDiscovery.class, LockEntry.class,
			LockInfo.class, LockRoot.class, LockScope.class, LockToken.class, LockTokenMatchesRequestUri.class, LockTokenSubmitted.class, LockType.class,
			MultiStatus.class, NoConflictingLock.class, NoExternalEntities.class, Owner.class, PreservedLiveProperties.class, Prop.class, PropertyUpdate.class,
			PropFind.class, PropFindFiniteDepth.class, PropName.class, PropStat.class, Remove.class, ResourceType.class, Response.class,
			ResponseDescription.class, Set.class, Shared.class, Status.class, SupportedLock.class, TimeOut.class, Write.class };

	@Test
	public final void createsJAXBContext() throws JAXBException {
		final Object context = new WebDavContextFactory().create();
		assertThat(context, instanceOf(JAXBContext.class));
	}

	@Theory
	public final void factoryPretendsToContainAllWebDavElements(final Class<?> webDavElement) throws JAXBException {
		final WebDavContextFactory factory = new WebDavContextFactory();
		assertThat(factory.contains(webDavElement), is(true));
	}

	@Test
	public final void containsCustomClasses() throws JAXBException {
		final WebDavContextFactory factory = new WebDavContextFactory(String.class, Integer.class);
		assertThat(factory.contains(String.class), is(true));
		assertThat(factory.contains(Integer.class), is(true));
	}
}
