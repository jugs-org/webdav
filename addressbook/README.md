# WebDAV AddressBook Example


This example demonstrates how to develop RESTful WebDAV service with the embedded Grizzly server.

To understand the code, JPA knowledge is not essential (just ignore the JPA parts to learn about the WebDAV parts).
Also, the code contains several patches to be compatible with the Microsoft WebDAV-Mini-Redirector client.

The mapping of the URI path space is presented in the following table:

| URI path     | Resource class | HTTP methods                                       |
|--------------|----------------|----------------------------------------------------|
| /addressbook | AddressBook    | OPTIONS, GET, PUT, PROPFIND, PROPPATCH, MOVE, COPY |


## Running the Example

### Preconditions

This example is using JPA to access a database, so you need to configure the database to use.
Note that JPA knowledge and a database system is needed to run the example.

* In META-INF/persistence.xml configure database access.
* The JDBC driver configured in that file must be found on the classpath.

As example an in-memory database (H2) is configured as default.

Run the example as follows:

    mvn compile exec:java

Using a WebDAV client, visit http://localhost:80/addressbook

### Notes

#### Windows

This sample was tested using the Microsoft WebDAV-Mini-Redirector of Windows XP Home SP 3.
If you also want to use this particular client, please note that it contains lots of bugs (see http://greenbytes.de/tech/webdav/webdav-redirector-list.html).
The following sequence was tested and worked well:

    NET USE * http://localhost/addressbook
    DIR Z:
    COPY C:\x.adr Z:\y.adr
    COPY Z:\y.adr Z:\z.adr
    COPY Z:\z.adr C:\z.adr
    TYPE Z:\z.adr
    EDIT Z:\z.adr
    DEL Z:\y.adr
    REN Z:\z.adr a.adr
    EXPLORER Z:\

To repeat this sample, you need the file x.adr or similar.
It is found in the folder [src/test/resources](src/test/resources).

#### Mac

To test it with MacOS open Finder and select "Connect to Server..." (command-K).
Type in the URL and connect as guest.
Normally the addressbook will be mounted as `/Volumes/addressbook`.