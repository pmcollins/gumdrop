# Gumdrop-Web

A library for handling HTTP requests and building dynamic HTML documents.

### Setup

At application startup, just register your [Controller](gumdrop/web/control/Controller.java)s with a single
[Dispatcher](gumdrop/web/control/Dispatcher.java), binding request patterns to Controllers.

For example, if you had a controller to show messages by id, you'd tell the dispatcher how to create new instances of
your controller and bind this to a request pattern.

```java
ShowMessageView showMessageView = new ShowMessageView();
dispatcher.register(GET, "/messages/#", () -> new ShowMessageController(
  sessionService, messageService, showMessageView, layoutView
));
```

The `#` is a numeric wildcard, so that GET requests to, for example `/submissions/42`, will be dispatched to the indicated
Controller. The matching number is passed to the Controller instance and made available via the `getPathArgs` method.

You don't pass in an actual controller instance, but rather a Controller Supplier (a lambda). Thus a Controller only
lives for one request, is handled by one thread, and doesn't have to be thread safe.

### General Control Flow

After the Dispatcher is set up, when an HTTP Request is received:

1. The [Dispatcher](gumdrop/web/control/Dispatcher.java) finds a matching
[Controller](gumdrop/web/control/Controller.java) lambda for the given request
2. The Dispatcher creates a Controller instance, which is populated with everything it needs to handle the request
3. The Controller instantiates and sets up a [Presenter](gumdrop/web/control/Presenter.java)
3. The Presenter generates a [ViewModel](gumdrop/web/html/ViewModel.java)
    * If necessary the parent Presenter also sets up sub-Presenters and attaches their ViewModels to the parent ViewModel
4. The Controller hands the ViewModel instance to a [View](gumdrop/web/html/View.java)
5. The View assembles a tree of [Widget](gumdrop/web/html/Widget.java)s, handing each widget any appropriate model data
6. The tree of Widgets is traversed, building an [HttpResponse](gumdrop/web/http/HttpResponse.java)
7. The HttpResponse is returned to the caller

### HTML Generation

Gumdrop provides a convenient library for generating HTML: [TabLib](gumdrop/web/html/TagLib.java). Instead of using a
template language, Gumdrop lets you create nested HTML-like tags in Java with its attendant compile-time safety,
refactoring capability, and performance.

An anchor Widget might look like this:

```java
import gumdrop.web.html.Buildable;
import gumdrop.web.html.Widget;

import static gumdrop.web.html.TagLib.a;

public class AnchorWidget extends Widget {

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

A slightly more complex Widget -- one that uses nested tags -- might look this this:

```java

import gumdrop.web.html.Buildable;
import gumdrop.web.html.Widget;

import static gumdrop.web.html.TagLib.*;

public class TitledPanelWidget extends Widget {

  private final Buildable titleBar;
  private final Buildable contents;

  public TitledPanelWidget(String title, Buildable contents) {
    titleBar = span(title);
    this.contents = contents;
  }

  public TitledPanelWidget(Buildable titleBar, Buildable contents) {
    this.titleBar = titleBar;
    this.contents = contents;
  }

  @Override
  protected Buildable getBuildable() {
    return section(
      header(titleBar),
      main(contents)
    );
  }

}

```

This panel widget wraps arbitrary content in a panel using the HTML 5 `section`, `header`, and `main` tags.
