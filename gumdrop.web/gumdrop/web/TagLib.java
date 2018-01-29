package gumdrop.web;

public class TagLib {

  public static Tag tag(String name, Buildable... children) {
    return new Tag(name).add(children);
  }

  public static Tag tag(String name, String child) {
    return new Tag(name).add(text(child));
  }

  public static Text text(String text) {
    return new Text(text);
  }

  public static Tag html(Buildable... children) {
    return tag("html", children);
  }

  public static Tag head(Buildable... children) {
    return tag("head", children);
  }

  public static Tag title(String text) {
    return tag("title", text(text));
  }

  public static Tag body(Buildable... children) {
    return tag("body", children);
  }

  public static Tag table(Buildable... children) {
    return tag("table", children);
  }

  public static Tag tr(Buildable... children) {
    return tag("tr", children);
  }

  public static Tag th(String text) {
    return tag("th", text(text));
  }

  public static Tag td(String text) {
    return tag("td", text(text));
  }

  public static Tag td(Buildable... children) {
    return tag("td", children);
  }

  public static Tag nav(Buildable... children) {
    return tag("nav", children);
  }

  public static Tag div(Buildable... children) {
    return tag("div", children);
  }

  public static Tag div(String text) {
    return div(text(text));
  }

  public static Tag a(String text) {
    return new Tag("a").add(text);
  }

  public static Tag span(String text) {
    return tag("span", text(text));
  }

  public static Tag span(Buildable... children) {
    return tag("span", children);
  }

  public static Tag form(Buildable... children) {
    return tag("form", children);
  }

  public static Tag label(Buildable... children) {
    return tag("label", children);
  }

  public static Tag input() {
    return tag("input");
  }

  public static Tag textarea() {
    Tag textarea = new Tag("textarea");
    textarea.setNonAutoClosing();
    return textarea;
  }

  public static Tag header(Buildable... children) {
    return tag("header", children);
  }

  public static Tag footer(Buildable... children) {
    return tag("footer", children);
  }

  public static Tag footer(String text) {
    return tag("footer", text(text));
  }

  private static Tag h1(Buildable... children) {
    return tag("h1", children);
  }

  public static Tag h1(String text) {
    return h1(text(text));
  }

  private static Tag h2(Buildable... children) {
    return tag("h2", children);
  }

  public static Tag h2(String text) {
    return h2(text(text));
  }

  private static Tag h3(Buildable... children) {
    return tag("h3", children);
  }

  public static Tag h3(String text) {
    return h3(text(text));
  }

  public static Tag h4(Buildable... children) {
    return tag("h4", children);
  }

  public static Tag h4(String text) {
    return h4(text(text));
  }

  private static Tag h5(Buildable... children) {
    return tag("h5", children);
  }

  public static Tag h5(String text) {
    return h5(text(text));
  }

  public static Tag dl(Buildable... children) {
    return tag("dl", children);
  }

  public static Tag dt(String text) {
    return tag("dt", text);
  }

  public static Tag dt(Buildable... children) {
    return tag("dt", children);
  }

  public static Tag dd(String text) {
    return tag("dd", text);
  }

  public static Tag dd(Buildable... children) {
    return tag("dd", children);
  }

  public static Tag select() {
    return tag("select");
  }

  public static Tag option(String text) {
    return tag("option", text);
  }

  public static Tag a() {
    return tag("link");
  }

  public static Tag p(String text) {
    return tag("p", text);
  }

  public static Tag p(Buildable... buildables) {
    return tag("p", buildables);
  }

  public static Tag ol(Buildable... buildables) {
    return tag("ol", buildables);
  }

  public static Tag li(Buildable... buildables) {
    return tag("li", buildables);
  }

  public static Tag li(String text) {
    return tag("li", text);
  }

  public static Tag ul(Buildable... buildables) {
    return tag("ul", buildables);
  }

}
