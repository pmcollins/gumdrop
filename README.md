# Gumdrop

I am building a web application from scratch, with no dependencies (outside of the JDK).

The parts of the application that are general purpose are being put here. The intent is to ultimately package
all of this into a framework usable by any web application developer.

* [Server](gumdrop.server) -- a tiny web server using Java's non-blocking IO
* [Web](gumdrop.web) -- for dispatching requests and building dynamic HTML documents
* [Json](gumdrop.json) -- for converting Java objects to and from JSON
* [Common](gumdrop.common) -- common classes (builder, http, validation, etc.)
* [Test](gumdrop.test) -- unit tests
