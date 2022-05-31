# WebDAV Fileserver


## Introduction

I received the sources of this module after before a presentation on the [JavaLand](http://www.javaland.eu) conference in 2022 from [Markus Karg](http://www.headcrashing.eu).
The old package name `net.java.dev.webdav.fileserver` is probably no longer tolerated and was changed to `org.jugs.webdav.fileserver`.
Also this project is now a module of the webdav project.


### Build

The first goal is to make the fileserver module runnable and testable.
At the moment there are some outdated libs like webdav-interop and JEE libs.
This will be updated in one of the next steps.

To build the module just go to the parent and build the whole project with

    mvn -DskipTests package

If this was successfull you can start the main class (see next section). 


### Launch Configuration

To start the fileserver use the following settings:

* VM Args: `-Djava.util.logging.config.file=src/main/resources/logging.properties`
* Main Class: `FileServerStarter`

After you have started the server you can connect to the WebDAV server with `http://localhost/fileserver`.
You should see the directory where the server was started, e.g.

    $  ls -F /Volumes/fileserver/
    README.md      pom.xml        src/           target/




---

## Summary

This short summary here is from the orginal project.
For a more detail description see above.

### How to Build

* build and install interop
* start class FileServerStarter

### How to Test

* WebDAV-Client for http://localhost/fileserver