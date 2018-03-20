package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Asserts.assertEquals("/person/42", group);
    String group1 = matcher.group(1);
    Asserts.assertEquals("42", group1);
  }

  private void innerMatch() {
    Pattern pattern = Pattern.compile("/person/" + DIGITS + "/profiles");
    Matcher matcher = pattern.matcher("/person/42/profiles");
    matcher.matches();
    String group1 = matcher.group(1);
    Asserts.assertEquals("42", group1);
  }

  private void nonMatches() {
    Pattern pattern = Pattern.compile("/person/" + DIGITS + "/profiles");
    Asserts.assertFalse(pattern.matcher("/person/42/profile").matches());
    Asserts.assertFalse(pattern.matcher("/person/42/profilesx").matches());
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
