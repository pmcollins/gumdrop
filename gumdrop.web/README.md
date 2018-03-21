# Gumdrop Web

A library for handling HTTP requests and building dynamic HTML documents.

### Control Flow

1. A [Dispatcher](gumdrop/web/control/Dispatcher.java) finds a matching Controller for a request
2. The Controller sets up a Presenter
3. The Presenter generates a ViewModel
4. The ViewModel is handed off to a View
5. The View assembles a bunch of Widgets

