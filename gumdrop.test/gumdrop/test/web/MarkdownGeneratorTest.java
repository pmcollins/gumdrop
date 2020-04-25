package gumdrop.test.web;

import gumdrop.common.ByteIterator;
import gumdrop.common.SubstringMatcher;
import gumdrop.test.util.Test;
import gumdrop.web.html.Buildable;
import gumdrop.web.html.Tag;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.web.html.TagLib.*;

public class MarkdownGeneratorTest extends Test {

  public static void main(String[] args) throws Exception {
    new MarkdownGeneratorTest().run();
  }

  @Override
  public void run() throws Exception {
    testSimple();
    testNewLine();
  }

  private static void testSimple() {
    MarkdownGenerator gen = new MarkdownGenerator();
    Tag tag = gen.process("x");
    assertEquals(div("x").withClass("md"), tag);
  }

  private static void testNewLine() {
    MarkdownGenerator gen = new MarkdownGenerator(new NewlineRule());
    Tag tag = gen.process("a\nb");
    assertEquals(div(text("a"), br(), text("b")), tag);
  }

  private static class NewlineRule implements MarkdownRule {

    @Override
    public Buildable process(String mdChunk) {
      SubstringMatcher newLineMatcher = new SubstringMatcher("\n");
      ByteIterator it = new ByteIterator(mdChunk);
      newLineMatcher.match(it);
      String left = newLineMatcher.getSubstring();
      String right = it.tailString();
      return null;
    }

  }

  private static class MarkdownGenerator {

    private final MarkdownRule[] rules;

    public MarkdownGenerator(MarkdownRule... rules) {
      this.rules = rules;
    }

    public Tag process(String md) {
      for (MarkdownRule rule : rules) {
        rule.process(md);
      }
      return div(text(md)).withClass("md");
    }

  }

  private interface MarkdownRule {

    Buildable process(String mdChunk);

  }

}
