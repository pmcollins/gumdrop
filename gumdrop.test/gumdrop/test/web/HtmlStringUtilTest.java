package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.web.http.HttpStringUtil;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertNull;

public class HtmlStringUtilTest extends Test {

  public static void main(String[] args) {
    new HtmlStringUtilTest().run();
  }

  @Override
  public void run() {
    parseString();
    parseHexEntity();
    parseNumericEntity();
    parseMultiEntityString();
    parseSpaces();
    nuthing();
    dolla();
    end();
    stripTags();
  }

  private static void parseNumericEntity() {
    String entity = "%40";
    String c = HttpStringUtil.parseEntity(entity);
    assertEquals("@", c);
  }

  private static void parseHexEntity() {
    String c = HttpStringUtil.parseEntity("%2A");
    assertEquals("*", c);
  }

  private static void parseString() {
    String s = "foo%40bar";
    String parsed = HttpStringUtil.unescape(s);
    assertEquals("foo@bar", parsed);
  }

  private static void parseMultiEntityString() {
    String parsed = HttpStringUtil.unescape("aaa%3dbbb%26ccc%3Dddd");
    assertEquals("aaa=bbb&ccc=ddd", parsed);
  }

  private static void parseSpaces() {
    String unescaped = HttpStringUtil.unescape("aaa+bbb");
    assertEquals("aaa bbb", unescaped);
  }

  private static void nuthing() {
    assertEquals("hello", HttpStringUtil.unescape("hello"));
  }

  private static void dolla() {
    String s = "x%24x";
    String unescaped = HttpStringUtil.unescape(s);
    System.out.println(unescaped);
    assertEquals("x$x", unescaped);
  }

  private static void end() {
    assertEquals("hello%", HttpStringUtil.unescape("hello%"));
    assertEquals("hell%o", HttpStringUtil.unescape("hell%o"));
  }

  private static void stripTags() {
    String replaced = HttpStringUtil.sanitizeHtml("<hello>");
    assertEquals("&lt;hello&gt;", replaced);
    assertNull(HttpStringUtil.sanitizeHtml(null));
  }

}
