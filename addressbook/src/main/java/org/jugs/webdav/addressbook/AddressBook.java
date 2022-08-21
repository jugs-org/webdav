/*
 * Copyright 2008, 2009 Markus KARG
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

package org.jugs.webdav.addressbook;

import static javax.ws.rs.core.HttpHeaders.CONTENT_LENGTH;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.PRECONDITION_FAILED;
import static org.jugs.webdav.jaxrs.Headers.*;
import static org.jugs.webdav.jaxrs.xml.properties.ResourceType.COLLECTION;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;

import org.jugs.webdav.jaxrs.methods.COPY;
import org.jugs.webdav.jaxrs.methods.MOVE;
import org.jugs.webdav.jaxrs.methods.OPTIONS;
import org.jugs.webdav.jaxrs.methods.PROPFIND;
import org.jugs.webdav.jaxrs.methods.PROPPATCH;
import org.jugs.webdav.jaxrs.xml.elements.HRef;
import org.jugs.webdav.jaxrs.xml.elements.MultiStatus;
import org.jugs.webdav.jaxrs.xml.elements.Prop;
import org.jugs.webdav.jaxrs.xml.elements.PropStat;
import org.jugs.webdav.jaxrs.xml.elements.PropertyUpdate;
import org.jugs.webdav.jaxrs.xml.elements.Response;
import org.jugs.webdav.jaxrs.xml.elements.Status;
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.jugs.webdav.jaxrs.xml.properties.DisplayName;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLength;
import org.jugs.webdav.jaxrs.xml.properties.GetContentType;
import org.jugs.webdav.jaxrs.xml.properties.GetLastModified;
import org.jugs.webdav.jaxrs.methods.LOCK;
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.LockDiscovery;

/**
 * Sole JAX-RS Resource of JPA Address Book Sample. 
 * 
 * @author Markus KARG (mkarg@dev.users.java.net)
 */
@Path("addressbook")
public final class AddressBook {

	private static final String ADDRESS_MIME = "application/address+xml";

	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("AddressBook");

	@OPTIONS
	public final javax.ws.rs.core.Response options() {
		return javax.ws.rs.core.Response.noContent().header(DAV, "1").build();
	}

	@SuppressWarnings("unchecked")
	@PROPFIND
	public final MultiStatus propfind(@Context final UriInfo uriInfo, @DefaultValue(DEPTH_INFINITY) @HeaderParam(DEPTH) final String depth) {
		final Response folder = new Response(new HRef(uriInfo.getRequestUri()), null, null, null, new PropStat(new Prop(new MicrosoftRedirectorPatch2(),
				new DisplayName("My Collection"), new CreationDate(new Date()), new GetLastModified(new Date()), COLLECTION), new Status(OK)));

		if (depth.equals(DEPTH_0))
			return new MultiStatus(folder);

		final Collection<Response> responses = new LinkedList<Response>(Collections.singletonList(folder));
		for (final Contact c : (List<Contact>) this.em().createNamedQuery("ListContacts").getResultList())
			responses.add(new Response(new HRef(uriInfo.getAbsolutePathBuilder().path(String.format("%s.adr", c.getMatchCode())).build()), null, null, null,
					new PropStat(new Prop(new MicrosoftRedirectorPatch2(), new DisplayName(String.format("%s %s", c.getLastName(), c.getFirstName())),
							new CreationDate(c.getCreationDate()), new GetLastModified(c.getLastModified()), new GetContentLength(0), new GetContentType(
									ADDRESS_MIME)), new Status(OK))));

		return new MultiStatus(responses.toArray(new Response[0]));
	}

	@PROPFIND
	@Path("{filename}.adr")
	public final MultiStatus propfind(@PathParam("filename") final String matchCode, @Context final UriInfo uriInfo) {
		final Contact c = (Contact) this.em().createNamedQuery("FindContactByMatchCode").setParameter(1, matchCode).getSingleResult();
		return new MultiStatus(new Response(new HRef(uriInfo.getAbsolutePathBuilder().build()), null, null, null, new PropStat(new Prop(
				new MicrosoftRedirectorPatch2(), new DisplayName(String.format("%s %s", c.getLastName(), c.getFirstName())), new CreationDate(c
						.getCreationDate()), new GetLastModified(c.getLastModified()), new GetContentLength(0), new GetContentType(ADDRESS_MIME)), new Status(
				OK))));
	}

	@PROPPATCH
	@Path("{filename}.adr")
	public final void proppatch(final InputStream body, @Context final Providers providers, @Context final HttpHeaders httpHeaders) throws IOException {
		final PropertyUpdate propertyUpdate = providers.getMessageBodyReader(PropertyUpdate.class, PropertyUpdate.class, new Annotation[0],
				MediaType.APPLICATION_XML_TYPE).readFrom(PropertyUpdate.class, PropertyUpdate.class, new Annotation[0], MediaType.APPLICATION_XML_TYPE,
				httpHeaders.getRequestHeaders(), body);

		System.out.println("PATCH PROPERTIES: " + propertyUpdate.list());

		/*
		 * TODO Patch properties in database.
		 */
	}

	@GET
	@Produces(ADDRESS_MIME)
	@Path("{filename}.adr")
	public final Contact get(@PathParam("filename") final String matchCode) {
		return (Contact) this.em().createNamedQuery("FindContactByMatchCode").setParameter(1, matchCode).getSingleResult();
	}

	@PUT
	@Consumes(ADDRESS_MIME)
	@Path("{filename}.adr")
	public final void put(final InputStream entityStream, @PathParam("filename") final String matchCode, @HeaderParam(CONTENT_LENGTH) final long contentLength,
			@Context final Providers providers, @Context final HttpHeaders httpHeaders) throws IOException {
		/*
		 * Workaround for Jersey issue #154 (see
		 * https://jersey.dev.java.net/issues/show_bug.cgi?id=154): Jersey will
		 * throw an exception and abstain from calling a method if the method
		 * expects a JAXB element body while the actual Content-Length is zero.
		 */

		final Contact entity = contentLength == 0 ? new Contact(matchCode, null, null, null) : providers.getMessageBodyReader(Contact.class, Contact.class,
				new Annotation[0], new MediaType("application", "address+xml")).readFrom(Contact.class, Contact.class, new Annotation[0],
				new MediaType("application", "address+xml"), httpHeaders.getRequestHeaders(), entityStream);

		/*
		 * End of #154 workaround
		 */

		final EntityManager em = this.em();
		final EntityTransaction t = em.getTransaction();
		t.begin();
		try {
			((Contact) em.createNamedQuery("FindContactByMatchCode").setParameter(1, matchCode).getSingleResult()).update(entity);
		} catch (final NoResultException e) {
			em.persist(entity);
		}
		t.commit();
	}

	@DELETE
	@Path("{filename}.adr")
	public final void delete(@PathParam("filename") final String matchCode) {
		final EntityManager em = this.em();
		final EntityTransaction t = em.getTransaction();
		t.begin();
		em.createNamedQuery("DeleteContactByMatchCode").setParameter(1, matchCode).executeUpdate();
		t.commit();
	}

	@MOVE
	@Path("{filename}.adr")
	public final void move(@PathParam("filename") final String sourceMatchCode, @HeaderParam(DESTINATION) final URI destination,
			@HeaderParam(OVERWRITE) final String overwrite) {
		final EntityManager em = this.em();
		final EntityTransaction t = em.getTransaction();
		t.begin();

		final Contact source;
		try {
			source = (Contact) em.createNamedQuery("FindContactByMatchCode").setParameter(1, sourceMatchCode).getSingleResult();
		} catch (final NoResultException e) {
			t.rollback();
			throw new WebApplicationException(NOT_FOUND);
		}

		final String[] destinationPathSegments = destination.getPath().split("/");
		final String lastDestinationPathSegment = destinationPathSegments[destinationPathSegments.length - 1];

		if (!lastDestinationPathSegment.endsWith(".adr")) {
			t.rollback();
			throw new WebApplicationException(FORBIDDEN);
		}

		final String destinationMatchCode = lastDestinationPathSegment.split("\\.")[0];

		Contact target;
		try {
			target = (Contact) em.createNamedQuery("FindContactByMatchCode").setParameter(1, destinationMatchCode).getSingleResult();
		} catch (final NoResultException e) {
			target = null;
		}

		if (target != null) {
			if (overwrite.equals(OVERWRITE_FALSE)) {
				t.rollback();
				throw new WebApplicationException(PRECONDITION_FAILED);
			}

			em.remove(target);
		}

		em.remove(source);

		em.persist(new Contact(destinationMatchCode, source));

		t.commit();
	}

	@COPY
	@Path("{filename}.adr")
	public final void copy(@PathParam("filename") final String sourceMatchCode, @HeaderParam(DESTINATION) final URI destination,
			@HeaderParam(OVERWRITE) final String overwrite) {

		final EntityManager em = this.em();
		final EntityTransaction t = em.getTransaction();
		t.begin();

		final Contact source;
		try {
			source = (Contact) em.createNamedQuery("FindContactByMatchCode").setParameter(1, sourceMatchCode).getSingleResult();
		} catch (final NoResultException e) {
			t.rollback();
			throw new WebApplicationException(NOT_FOUND);
		}

		final String[] destinationPathSegments = destination.getPath().split("/");
		final String lastDestinationPathSegment = destinationPathSegments[destinationPathSegments.length - 1];

		if (!lastDestinationPathSegment.endsWith(".adr")) {
			t.rollback();
			throw new WebApplicationException(FORBIDDEN);
		}

		final String destinationMatchCode = lastDestinationPathSegment.split("\\.")[0];

		Contact target;
		try {
			target = (Contact) em.createNamedQuery("FindContactByMatchCode").setParameter(1, destinationMatchCode).getSingleResult();
		} catch (final NoResultException e) {
			target = null;
		}

		if (target != null) {
			if (overwrite.equals(OVERWRITE_FALSE)) {
				t.rollback();
				throw new WebApplicationException(PRECONDITION_FAILED);
			}

			em.remove(target);
		}

		em.persist(new Contact(destinationMatchCode, source));

		t.commit();
	}

//	@LOCK
//	@Path("{filename}.adr")
//	public javax.ws.rs.core.Response lock(@PathParam("filename") final String matchCode, @Context final UriInfo uriInfo) {
//		LockDiscovery lockDiscovery =
//			new LockDiscovery(new ActiveLock(LockScope.SHARED, LockType.WRITE, Depth.ZERO, new Owner(""), new TimeOut(75), new LockToken(new org.jugs.webdav.jaxrs.xml.elements.HRef(
//				"http://localhost")), new LockRoot(new org.jugs.webdav.jaxrs.xml.elements.HRef("http://localhost"))));
//		org.jugs.webdav.jaxrs.xml.elements.Prop prop = new org.jugs.webdav.jaxrs.xml.elements.Prop(lockDiscovery);
//		return javax.ws.rs.core.Response.ok(prop)
//				.header(DAV, "1")
//				.header(LOCK_TOKEN, "1")
//				.build();
//	}

	private final EntityManager em() {
		return this.emf.createEntityManager();
	}

}
