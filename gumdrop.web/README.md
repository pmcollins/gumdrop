# Gumdrop Web

A library for handling HTTP requests and building dynamic HTML documents.

### Control Flow

When an HTTP request is received:

1. A [Dispatcher](gumdrop/web/control/Dispatcher.java) finds a matching [Controller](gumdrop/web/control/Controller.java)
2. The Controller sets up a [Presenter](gumdrop/web/control/Presenter.java)
3. The Presenter generates a [ViewModel](gumdrop/web/html/ViewModel.java)
    * and if necessary sets up sub-Presenters whose ViewModels are attached to the parent ViewModel
4. The ViewModel is handed off to a [View](gumdrop/web/html/View.java)
5. The View assembles a tree of [Widget](gumdrop/web/html/Widget.java)s, handing each widget any appropriate model data
6. The tree of Widgets is traversed, building a response string
