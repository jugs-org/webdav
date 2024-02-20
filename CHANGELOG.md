# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [Unreleased]

* update to Jersey 3.1
* fileserver logging improved


## [3.0] - 2023-02-18

* update to Jersey 3
* fileserver logging expanded


## [2.5] - 2023-01-02

* fileserver supports also HTML for file browsing
* switched to Grizzly 2 (jersey-grizzly2)
* JAXB provider added as runtime dependency
* rebase with [webdav-jaxrs](https://gitlab.com/headcrashing/webdav-jaxrs)


## [2.4] - 2022-10-01

* examples extended with **lock support** (works now also on Mac)
* SLF4J added as logging layer before java.util.logging
* dependency to old `net.java.dev.webdav` dependency completely removed
* unit tests migrated to JUnit 5


## [2.3] - 2022-08-05

* [addressbook](addressbook/README.md) added as new module
* fileserver switched to org.jugs.webdav:webdav-jaxrs as base lib


## [2.2] - 2022-07-12

* [webdav-interop](webdav-interop/README.md) added as new module


## [2.1] - 2022-06-09

* fileserver example added as new module
* transfered documentation for Doxia format (.apt) to Mark Down (.md) and ASCIIDOC (.adoc)
* logo added


## [2.0] - 2022-05-14

* **Support for JAX-RS 2.0**
* new home ([OSSRH-80762](https://issues.sonatype.org/browse/OSSRH-80762)), 
  new groupId and new base package (`org.jugs`)
* Several minor improvements (i.e. succesful compiled with Java 11)
* Several bug fixes
* cloned from https://gitlab.com/oboehm/webdav-jaxrs

### Support for JAX-RS 2.0

Enabling WebDAV and configuring custom extensions is totally simplified: Thanks to auto discovery, there is no more need to write an implementation of the `Application` interface.

>**Note**:
The API of version 2.x is purposely slightly incompatible with version 1.x: As the main driver for version 2.0 was support for the new and vastly simplified JAX-RS 2.0 API, we deprecated `WebDavContextResolver`, as still using that class in applications foils the sole idea of that simplified API. Instead use the new `WebDAV` feature.


## [1.2.1] - 2014-02-02

* Several minor improvements.

## [1.2] - 2014-01-06

>**Note**:
Due to technical problems version 1.2 found in Maven Central is broken. Please instead use the version number **1.2-1** to depend on version 1.2.

* Several performance improvements.
* Several bug fixes.
* More singletons instead creating "empty" XML elements.

  Using singletons instead of creating multiple equal instances of otherwise "empty" elements has the potential for easier-to-read source code, reduced memory footprint of the in-memory model, higher marshalling speed and comparison by `==` instead of `equals()`. Hence, do not further use code like `new AllProp()` but instead use `AllProp.ALLPROP` and compare against constants using `== AllProp.ALLPROP`.

* Deprecated several methods.
* 2011-07-30 Moved to new java.net (Kenai based) hosting

## [1.1.1] - 2009-12-29

## [1.1] - 2009-12-09

* 2009-11-27 API adjustments according to JAX-RS 1.1 (@OPTIONS and some other items now are deprecated: Use JAX-RS 1.1's replacements instead.).
* 2009-03-03 New reference user of webdav-jaxrs: [EADS](webdav-jaxrs/src/site/resources/images/EADS_A_4c_small.JPG) (vendor of Airbus etc.), also first user running this on Mac.
* 2009-02-17 New site, containing installation, configuration, road map and faq

## [1.0] - 2009-02-14
