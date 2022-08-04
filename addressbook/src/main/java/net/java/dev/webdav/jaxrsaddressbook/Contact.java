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

package net.java.dev.webdav.jaxrsaddressbook;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * Simple contact JPA entity.<br>
 * 
 * @author Markus KARG (mkarg@dev.users.java.net)
 */
@XmlRootElement(namespace = "sample")
@Entity
@Table
@NamedQueries( { @NamedQuery(name = "ListContacts", query = "SELECT c FROM Contact c"),
		@NamedQuery(name = "FindContactByMatchCode", query = "SELECT c FROM Contact c WHERE c.matchCode = ?1"),
		@NamedQuery(name = "DeleteContactByMatchCode", query = "DELETE FROM Contact c WHERE c.matchCode = ?1") })
public final class Contact {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false, unique = true)
	private String matchCode;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	private String firstName;

	private String lastName;

	private String eMail;

	@SuppressWarnings("unused")
	private Contact() {
		// For unmarshalling and jpa loading only.
	}

	public Contact(final String matchCode, final String firstName, final String lastName, final String eMail) {
		if (matchCode == null)
			throw new NullArgumentException("matchCode");

		this.matchCode = matchCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		this.creationDate = new Date();
		this.lastModified = new Date();
	}

	public Contact(final String matchCode, final Contact source) {
		if (matchCode == null)
			throw new NullArgumentException("matchCode");

		if (source == null)
			throw new NullArgumentException("source");

		this.matchCode = matchCode;
		this.firstName = source.firstName;
		this.lastName = source.lastName;
		this.eMail = source.eMail;
		this.creationDate = source.creationDate;
		this.lastModified = source.lastModified;
	}

	@XmlTransient
	public final String getMatchCode() {
		return this.matchCode;
	}

	public final void setMatchCode(final String matchCode) {
		this.matchCode = matchCode;
	}

	@XmlTransient
	public final Date getCreationDate() {
		return this.creationDate;
	}

	@XmlTransient
	public final Date getLastModified() {
		return this.lastModified;
	}

	public final void setLastModified(final Date lastModified) {
		this.lastModified = lastModified;
	}

	public final String getFirstName() {
		return this.firstName;
	}

	public final void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public final String getLastName() {
		return this.lastName;
	}

	public final void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public final String getEMail() {
		return this.eMail;
	}

	public final void setEMail(final String eMail) {
		this.eMail = eMail;
	}

	public final void update(final Contact source) {
		this.eMail = source.eMail;
		this.firstName = source.firstName;
		this.lastName = source.lastName;
		this.lastModified = new Date();
	}

	@Override
	public final String toString() {
		return String.format("%d: %s, %s (%s)", this.id, this.lastName, this.firstName, this.eMail);
	}

}
