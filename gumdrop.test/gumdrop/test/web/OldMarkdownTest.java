package gumdrop.test.web;

import gumdrop.common.ByteIterator;
import gumdrop.common.SubstringMatcher;
import gumdrop.test.util.Test;
import gumdrop.web.html.Buildable;
import gumdrop.web.html.Buildables;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.web.html.TagLib.*;

public class OldMarkdownTest extends Test {

  public static void main(String[] args) {
    new OldMarkdownTest().run();
  }

  @Override
  public void run() {
    testEmptyString();
    testSingleLine();
    testMultiLine();
    testMultiLine2();
  }

  private static void testEmptyString() {
    Buildable converted = Markdown.convert("");
    String s = buildableToString(converted);
    assertEquals("<div class=\"md\"></div>", s);
  }

  private static void testSingleLine() {
    Buildable converted = Markdown.convert("x");
    String s = buildableToString(converted);
    assertEquals("<div class=\"md\">x</div>", s);
  }

  private static void testMultiLine() {
    Buildable converted = Markdown.convert("x\n");
    String s = buildableToString(converted);
    assertEquals("<div class=\"md\">x<br/></div>", s);
  }

  private static void testMultiLine2() {
    Buildable converted = Markdown.convert("x\ny");
    String s = buildableToString(converted);
    assertEquals("<div class=\"md\">x<br/>y</div>", s);
  }

  private static String buildableToString(Buildable buildable) {
    StringBuilder sb = new StringBuilder();
    buildable.build(sb);
    return sb.toString();
  }

  static class Markdown {

    static Buildable convert(String markdown) {
      SubstringMatcher m = new SubstringMatcher("\n");
      ByteIterator mdIt = new ByteIterator(markdown);
      boolean match = m.match(mdIt);
      String substring = mdIt.substring();
      Buildables contents = new Buildables(text(substring));
      if (match) {
        contents.add(br());
      }
      return div(contents).withClass("md");
    }

  }

}
