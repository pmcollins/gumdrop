package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.web.HttpStringUtil;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertNull;

public class HtmlStringUtilTest extends Test {

  public static void main(String[] args) {
    new HtmlStringUtilTest().run();
  }

  @Override
  public void run() {
    parseNumericEntity();
    parseHexEntity();
    parseString();
    parseMultiEntityString();
    parseSpaces();

    stripTags();
  }

  private void parseNumericEntity() {
    String entity = "%40";
    String c = HttpStringUtil.parseEntity(entity);
    assertEquals("@", c);
  }

  private void parseHexEntity() {
    String c = HttpStringUtil.parseEntity("%2A");
    assertEquals("*", c);
  }

  private void parseString() {
    String s = "foo%40bar";
    String parsed = HttpStringUtil.unescape(s);
    assertEquals("foo@bar", parsed);
  }

  private void parseMultiEntityString() {
    String parsed = HttpStringUtil.unescape("aaa%3dbbb%26ccc%3Dddd");
    assertEquals("aaa=bbb&ccc=ddd", parsed);
  }

  private void parseSpaces() {
    String unescaped = HttpStringUtil.unescape("aaa+bbb");
    assertEquals("aaa bbb", unescaped);
  }

  private void stripTags() {
    String replaced = HttpStringUtil.stripTags("<hello>");
    assertEquals("&lt;hello&gt;", replaced);
    assertNull(HttpStringUtil.stripTags(null));
  }

}
