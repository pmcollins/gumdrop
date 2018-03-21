# Gumdrop

I am building a web application from scratch, with no dependencies (outside of the JDK).

The parts of the application that have general applicability are being put here. The intent is to utlimately package
all of this into a framework.

* [Server](gumdrop.server) -- a tiny web server using Java's non-blocking IO
* [Web](gumdrop.web) -- for dispatching requests and building dynamic HTML documents
* [Json](gumdrop.json) -- for converting Java object to and from JSON
* [Common](gumdrop.common) -- common classes (builder, http, validation, etc.)
* [Test](gumdrop.test) -- unit tests
