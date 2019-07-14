package gumdrop.test.json.v2;

import gumdrop.json.v2.JsonDelegate;
import gumdrop.json.v2.JsonParser;
import gumdrop.test.util.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gumdrop.test.util.Asserts.assertListEquals;
import static gumdrop.test.util.Asserts.assertTrue;

public class JsonParserV2Test extends Test {

  public static void main(String[] args) throws Exception {
    new JsonParserV2Test().run();
  }

  @Override
  public void run() throws Exception {
//    str1();
    array();
    strArray1();
    strArray2();
    map1();
    mapOfArray();
    arrayOfMap();
  }

  private void str1() {
    FakeJsonDelegate d = new FakeJsonDelegate();
    JsonParser jsonParser = new JsonParser(d, "\"x\"");
    jsonParser.readValue();
    assertCommands(d, "accept [x]");
  }

  private void array() {
    FakeJsonDelegate d = new FakeJsonDelegate();
    JsonParser jsonParser = new JsonParser(d, "[]");
    jsonParser.readValue();
    assertCommands(d, "push", "pop");
  }

  private void strArray1() {
    FakeJsonDelegate d = new FakeJsonDelegate();
    JsonParser jsonParser = new JsonParser(d, "[\"x\"]");
    jsonParser.readValue();
    assertCommands(d, "push", "push", "accept [x]", "pop", "pop");
  }

  private void strArray2() {
    FakeJsonDelegate d = new FakeJsonDelegate();
    JsonParser jsonParser = new JsonParser(d, "[\"x\",\"y\"]");
    jsonParser.readValue();
    assertCommands(d, "push", "push", "accept [x]", "pop", "push", "accept [y]", "pop", "pop");
  }

  private void map1() {
    FakeJsonDelegate d = new FakeJsonDelegate();
    JsonParser jsonParser = new JsonParser(d, "{\"a\":\"x\",\"b\":\"y\"}");
    jsonParser.readValue();
    assertCommands(d, "push", "push [a]", "accept [x]", "pop", "push [b]", "accept [y]", "pop", "pop");
  }

  private void mapOfArray() {
    FakeJsonDelegate d = new FakeJsonDelegate();
    JsonParser jsonParser = new JsonParser(d, "{\"a\":[\"b\"]}");
    jsonParser.readValue();
    assertCommands(d, "push", "push [a]", "push", "push", "accept [b]", "pop", "pop", "pop", "pop");
  }

  private void arrayOfMap() {
    FakeJsonDelegate d = new FakeJsonDelegate();
    JsonParser jsonParser = new JsonParser(d, "[{\"a\":\"x\"}]");
    jsonParser.readValue();
    assertCommands(d, "push", "push", "push", "push [a]", "accept [x]", "pop", "pop", "pop", "pop");
  }

  private static void assertCommands(FakeJsonDelegate d, String... cmds) {
    assertTrue(d.isZeroLevel());
    assertListEquals(List.of(cmds), d.getCommands());
  }

  private static class FakeJsonDelegate implements JsonDelegate {

    private final List<String> commands = new ArrayList<>();
    private int level;

    @Override
    public void push(String string) {
      level++;
      commands.add("push [" + string + "]");
    }

    @Override
    public void pop(String string) {
      level--;
      commands.add("pop [" + string + "]");
    }

    @Override
    public void acceptString(String val) {
      commands.add("acceptString [" + val + "]");
    }

    @Override
    public void push() {
      level++;
      commands.add("push");
    }

    @Override
    public void pop() {
      level--;
      commands.add("pop");
    }

    @Override
    public void acceptBareword(String bareword) {
      commands.add("acceptBareword [" + bareword + "]");
    }

    List<String> getCommands() {
      return commands;
    }

    boolean isZeroLevel() {
      return level == 0;
    }

  }

}
