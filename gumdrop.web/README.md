# Gumdrop-Web

A library for handling HTTP requests and building dynamic HTML documents.

### Setup

At application startup, we register our [Controller](gumdrop/web/control/Controller.java)s with a single
[Dispatcher](gumdrop/web/control/Dispatcher.java), associating each Controller with a request pattern.

For example, if we have a Controller to show messages by id, and we want our server to respond to requests to
`/messages/<message-id>`, we'd tell our Dispatcher how to create new instances of our controller and bind that to the
request pattern, `/messages/#`.

```java
Dispatcher dispatcher = new Dispatcher();
ShowMessageView showMessageView = new ShowMessageView();
dispatcher.register(GET, "/messages/#", () -> new ShowMessageController(
  sessionService, messageService, showMessageView, layoutView
));
```

The `#` is a numeric wildcard. Using a numeric wildcard in `/messages/#` means that GET requests to, for example
`/messages/42`, will match, with the matching number passed to the Controller instance.

### General Control Flow

After our Dispatcher is set up, when an HTTP Request is received:

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

Gumdrop provides a convenient library for generating HTML. Instead of using a template language, Gumdrop lets you create
nested HTML-like tags in Java with its attendant compile-time safety, refactoring capability, and performance.

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

That static `a` method returns a [Tag](gumdrop/web/html/Tag.java), for which we set the `href` value to the `url` attribute of our `AnchorWidget`
instance.

A slightly more complex Widget -- one that uses a `section`, and nested `header` and `main` tags -- might look this this:

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

In this case, we create header and main tags as children of our section tag and end up with an HTML structure that looks
like our method call structure:

```html

<section>
  <header>titleBar stuff</header>
  <main>content stuff</main>
</section>

```
