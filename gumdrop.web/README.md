# Gumdrop Web

A library for handling HTTP requests and building dynamic HTML documents.

### Control Flow

1. A [Dispatcher](gumdrop/web/control/Dispatcher.java) finds a matching [Controller](gumdrop/web/control/Controller.java) for a request
2. The Controller sets up a [Presenter](gumdrop/web/control/Presenter.java)
3. The Presenter generates a [ViewModel](gumdrop/web/control/ViewModel.java)
4. The ViewModel is handed off to a [View](gumdrop/web/html/View.java)
5. The View assembles a collection of [Widget](gumdrop/web/html/Widget.java)s

