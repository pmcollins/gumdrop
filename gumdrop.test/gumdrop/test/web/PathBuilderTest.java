package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.web.control.PathBuilder;

import java.util.UUID;

import static gumdrop.test.util.Asserts.assertEquals;

public class PathBuilderTest extends Test {

  public static void main(String[] args) {
    new PathBuilderTest().run();
  }

  @Override
  public void run() {
    zero();
    one();
    oneTrailing();
    two();
    twoTrailing();
    three();
    wildcard();
    onePlusWildcard();
  }

  private void zero() {
    PathBuilder pathBuilder = new PathBuilder("/people");
    String path = pathBuilder.build();
    assertEquals("/people", path);
  }

  private void one() {
    PathBuilder pathBuilder = new PathBuilder("/person/#");
    String path = pathBuilder.build(42);
    assertEquals("/person/42", path);
  }

  private void oneTrailing() {
    PathBuilder pathBuilder = new PathBuilder("/person/#/submissions");
    String path = pathBuilder.build(42);
    assertEquals("/person/42/submissions", path);
  }

  private void two() {
    PathBuilder pathBuilder = new PathBuilder("/person/#/submission/#");
    String path = pathBuilder.build(42, 111);
    assertEquals("/person/42/submission/111", path);
  }

  private void twoTrailing() {
    PathBuilder pathBuilder = new PathBuilder("/person/#/submission/#/comments");
    String path = pathBuilder.build(42, 111);
    assertEquals("/person/42/submission/111/comments", path);
  }

  private void three() {
    PathBuilder pathBuilder = new PathBuilder("/person/#/submission/#/comment/#");
    String path = pathBuilder.build(42, 111, 99);
    assertEquals("/person/42/submission/111/comment/99", path);
  }

  private void wildcard() {
    PathBuilder pathBuilder = new PathBuilder("/prompts/private/*");
    String uuid = UUID.randomUUID().toString();
    String path = pathBuilder.build(uuid);
    assertEquals("/prompts/private/" + uuid, path);
  }

  private void onePlusWildcard() {
    PathBuilder pathBuilder = new PathBuilder("/prompts/#/private/*");
    String uuid = UUID.randomUUID().toString();
    String path = pathBuilder.build("42", uuid);
    assertEquals("/prompts/42/private/" + uuid, path);
  }

}
