# Gumdrop Web

A library for handling HTTP requests and building dynamic HTML documents.

### Setup

At application startup, register [Controller](gumdrop/web/control/Controller.java)s with a single
[Dispatcher](gumdrop/web/control/Dispatcher.java).

For example, if you had a controller to show messages having some id, you'd decide on a URL convention and register it
with your Dispatcher instance:

```java
ShowMessageView showMessageView = new ShowMessageView();
dispatcher.register(GET, "/messages/#", () -> new ShowMessageController(
  sessionService, messageService, showMessageView, layoutView
));
```

The `#` is a numeric wildcard, so that GET requests to for example `/submissions/42` are dispatched to the indicated
Controller with the matching "group" (in this case just a number) passed to the Controller instance and made available
via the `getPathArgs` method. 

Notice that you don't pass in an actual controller instance, but rather a Controller Supplier (lambda). This is because
a new controller is instantiated for each request. The nice thing about the lifetime of a Controller being short -- that
of a single request -- is that you don't have to worry about them being thread safe.

### General Control Flow

After the Dispatcher is set up, when an HTTP Request is received:

1. The [Dispatcher](gumdrop/web/control/Dispatcher.java) finds a matching
[Controller](gumdrop/web/control/Controller.java) lambda for the given request path and method
2. The Dispatcher creates a Controller instance, which is populated with everything it needs to handle the request
3. The Controller instantiates and sets up a [Presenter](gumdrop/web/control/Presenter.java)
3. The Presenter generates a [ViewModel](gumdrop/web/html/ViewModel.java)
    * If necessary it also sets up sub-Presenters and attaches their ViewModels to the parent ViewModel
4. The Controller hands the ViewModel instance to a [View](gumdrop/web/html/View.java)
5. The View assembles a tree of [Widget](gumdrop/web/html/Widget.java)s, handing each widget any appropriate model data
6. The tree of Widgets is traversed, building an [HttpResponse](gumdrop/web/http/HttpResponse.java)
7. The HttpResponse is returned to the caller

### HTML Generation

Gumdrop provides a convenient library for generating HTML: [TabLib](gumdrop/web/html/TagLib.java). Instead of using a
template language, Gumdrop lets you create nested HTML-like tags in Java with the compile-time safety, refactoring
capability, and performance of Java.

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

A slightly more complex Widget -- one that uses nested tags -- might look this this.

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
