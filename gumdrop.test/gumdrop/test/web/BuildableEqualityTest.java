package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.web.html.Tag;
import gumdrop.web.html.TagAttribute;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.web.html.TagLib.div;

public class BuildableEqualityTest extends Test {

  public static void main(String[] args) throws Exception {
    new BuildableEqualityTest().run();
  }

  @Override
  public void run() throws Exception {
    testTagAndTextEquality();
    testTagAttributeEquality();
  }

  private static void testTagAndTextEquality() {
    Tag a = div("a");
    Tag b = div("a");
    assertEquals(a, b);
  }

  private static void testTagAttributeEquality() {
    TagAttribute a = new TagAttribute("a", "b");
    TagAttribute b = new TagAttribute("a", "b");
    assertEquals(a, b);
  }

}
