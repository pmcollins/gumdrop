# Gumdrop-Web

A library for handling HTTP requests and building dynamic HTML documents.

### Setup

At application startup, we register our [Controller](gumdrop/web/controller/Controller.java)s with a single
[Dispatcher](gumdrop/web/controller/Dispatcher.java), associating each Controller with a request pattern.

For example, if we have a Controller to show messages by id, and we want our server to respond to requests to
`/messages/<message-id>`, we'd tell our Dispatcher how to create new instances of our controller and bind that to the
request pattern, `/messages/#`.

```java

Dispatcher dispatcher = new Dispatcher();
dispatcher.register(GET, "/messages/#", () -> new ShowMessageController());

```

The `#` is a numeric wildcard. Using a numeric wildcard in `/messages/#` means that GET requests to, for example
`/messages/42`, will match, with the matching number passed to the Controller instance.

We then pass our dispatcher to `NioServer` along with a port number and call `run` to start the
[Gumdrop Server](../gumdrop.server/).

```java

new NioServer(dispatcher, 8080).run();

```

### HTML Generation

Gumdrop provides a convenient library for generating HTML. Instead of using a template language, we create
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

In this case, we create `header` and `main` tags as children of our `section` tag and end up with an HTML structure that
looks like our method call structure:

```html

<section>
  <header>titleBar stuff</header>
  <main>content stuff</main>
</section>

```
