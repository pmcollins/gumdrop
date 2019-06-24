package gumdrop.test.json.v2;

import gumdrop.json.v2.JsonDelegate;
import gumdrop.json.v2.JsonParser;
import gumdrop.test.util.Test;

public class JsonParserTest extends Test {

  public static void main(String[] args) throws Exception {
    new JsonParserTest().run();
  }

  @Override
  public void run() throws Exception {
    array();
  }

  private void array() {
    JsonParser parser = new JsonParser("[0]", new FakeJsonDelegate());
    parser.readValue();
  }

  private static class FakeJsonDelegate implements JsonDelegate {

    @Override
    public void push(String string) {
      System.out.println("push [" + string + "]");
    }

    @Override
    public void pop(String string) {
      System.out.println("pop [" + string + "]");
    }

    @Override
    public void push() {
      System.out.println("push");
    }

    @Override
    public void pop() {
      System.out.println("pop");
    }

  }

}
