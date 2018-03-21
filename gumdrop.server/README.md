# Gumdrop Server

A tiny web server using Java's non-blocking IO library.

The core networking part of the server is single threaded and non-blocking. What this means is that the part of the server
that delivers bytes to and from the worker threads doesn't wait for network data from any particular socket. Instead
it continuously scans all sockets and just grabs data that's already available and hands it off to the appropriate
worker thread. A blocking io server, on the other hand, dedicates an entire thread to each active socket, and waits
(blocks) for data, limiting the number of active sockets to the number of active threads. Using nio means a web server
can handle a huge number of simultaneous connections while still being able to process work in parallel.
