package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.web.HtmlEscapist;

import static gumdrop.test.util.Asserts.assertEquals;

public class HtmlEscapistTest extends Test {

  public static void main(String[] args) {
    new HtmlEscapistTest().run();
  }

  @Override
  public void run() {
    parseNumericEntity();
    parseHexEntity();
    parseString();
    parseMultiEntityString();
  }

  private void parseNumericEntity() {
    String entity = "%40";
    String c = HtmlEscapist.parseEntity(entity);
    assertEquals("@", c);
  }

  private void parseHexEntity() {
    String c = HtmlEscapist.parseEntity("%2A");
    assertEquals("*", c);
  }

  private void parseString() {
    String s = "foo%40bar";
    String parsed = HtmlEscapist.unescape(s);
    assertEquals("foo@bar", parsed);
  }

  private void parseMultiEntityString() {
    String parsed = HtmlEscapist.unescape("aaa%3dbbb%26ccc%3Dddd");
    assertEquals("aaa=bbb&ccc=ddd", parsed);
  }

}
