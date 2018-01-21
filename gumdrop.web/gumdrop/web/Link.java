package gumdrop.web;

import static gumdrop.web.TagLib.a;

public class Link extends Widget {

  private final String text, url;

  public Link(String text, String url) {
    this.text = text;
    this.url = url;
  }

  @Override
  protected Buildable getBuildable() {
    return a(text).attr("href", url);
  }

}
