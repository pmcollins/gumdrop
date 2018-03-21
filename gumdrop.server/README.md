# Gumdrop Server

A web server that uses Java's non-blocking IO library.

The part of the server that delivers bytes to and from the worker threads is single threaded and non-blocking. This
just means that the thread doesn't wait for network data from any particular socket. Instead, it continuously scans all
sockets, grabs data that's already available, and hands it off to the appropriate worker thread. A blocking IO server,
on the other hand, dedicates an entire thread to each active socket, each of which waits (blocks) for data, limiting the
number of active sockets to the number of active threads.

Using nio means your server can handle a very large number of simultaneous connections, but it can still use a
configurable number of threads to handle the relatively heavy lifting of processing requests.
