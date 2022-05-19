# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


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
