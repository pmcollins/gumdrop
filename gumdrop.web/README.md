# Gumdrop Web

A library for handling HTTP requests and building dynamic HTML documents.

### Setup

At application startup, register [Controller](gumdrop/web/control/Controller.java)s with a single
[Dispatcher](gumdrop/web/control/Dispatcher.java).

```
    ShowSubmissionView showSubmissionView = new ShowSubmissionView();
    dispatcher.register(GET, "/submissions/#", () -> new ShowSubmissionController(
      sessionService, submissionService, showSubmissionView, layoutView
    ));
```

The lifetime of a Controller is that of a single request, so you don't have to worry about them being thread safe.

### Control Flow

When an HTTP Request is received:

1. A [Dispatcher](gumdrop/web/control/Dispatcher.java) finds a matching [Controller](gumdrop/web/control/Controller.java)
2. The Controller sets up a [Presenter](gumdrop/web/control/Presenter.java)
3. The Presenter generates a [ViewModel](gumdrop/web/html/ViewModel.java)
    * If necessary sets up sub-Presenters and attaches their ViewModels to the parent ViewModel
4. The ViewModel is handed off to a [View](gumdrop/web/html/View.java)
5. The View assembles a tree of [Widget](gumdrop/web/html/Widget.java)s, handing each widget any appropriate model data
6. The tree of Widgets is traversed, building an [HttpResponse](gumdrop/web/http/HttpResponse.java)
7. The HttpResponse is returned to the caller

### HTML Generation

Gumdrop provides a convenient library for building up HTML: [TabLib](gumdrop/web/html/TagLib.java). Instead of using a
template language bound to some data, Gumdrop lets you create nested HTML-like tags in Java with the compile-time
safety, refactoring capability, and performance of Java.

A class that generates an HTML anchor tag might look like this:

```
import gumdrop.web.html.Buildable;
import gumdrop.web.html.Widget;

import static gumdrop.web.html.TagLib.a;

public class LinkWidget extends Widget {

  private final String text, url;

  public LinkWidget(String text, String url) {
    this.text = text;
    this.url = url;
  }

  @Override
  protected Buildable getBuildable() {
    return a(text).attr("href", url);
  }

}
```