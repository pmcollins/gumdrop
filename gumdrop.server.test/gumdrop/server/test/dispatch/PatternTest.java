package gumdrop.server.test.dispatch;

import gumdrop.test.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gumdrop.test.TestUtil.assertEquals;
import static gumdrop.test.TestUtil.assertFalse;

class PatternTest extends Test {

  private static final String DIGITS = "(\\d+)";

  public static void main(String[] args) {
    new PatternTest().run();
  }

  @Override
  public void run() {
    trailingMatch();
    innerMatch();
    nonMatches();
    doubleMatch();
  }

  private void trailingMatch() {
    Pattern pattern = Pattern.compile("/person/" + DIGITS);
    Matcher matcher = pattern.matcher("/person/42");
    matcher.matches();
    String group = matcher.group();
    assertEquals("/person/42", group);
    String group1 = matcher.group(1);
    assertEquals("42", group1);
  }

  private void innerMatch() {
    Pattern pattern = Pattern.compile("/person/" + DIGITS + "/profiles");
    Matcher matcher = pattern.matcher("/person/42/profiles");
    matcher.matches();
    String group1 = matcher.group(1);
    assertEquals("42", group1);
  }

  private void nonMatches() {
    Pattern pattern = Pattern.compile("/person/" + DIGITS + "/profiles");
    assertFalse(pattern.matcher("/person/42/profile").matches());
    assertFalse(pattern.matcher("/person/42/profilesx").matches());
  }

  private void doubleMatch() {
    Pattern pattern = Pattern.compile("/person/" + DIGITS + "/profile/" + DIGITS);
    Matcher matcher = pattern.matcher("/person/42/profile/111");
    matcher.matches();
    for (int i = 1; i <= matcher.groupCount(); i++) {
      String group = matcher.group(i);
      System.out.println("group = [" + group + "]");
    }
  }

}
