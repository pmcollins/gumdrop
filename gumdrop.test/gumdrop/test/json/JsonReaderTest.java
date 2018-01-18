package gumdrop.test.json;

import gumdrop.json.JsonReader;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import java.util.List;

class JsonReaderTest extends Test {

  public static void main(String[] args) {
    JsonReaderTest test = new JsonReaderTest();
    test.run();
  }

  @Override
  public void run() {
    skipWhiteSpace();
    skipNoWhiteSpace();
    readQuotedString();
    readBareword();
    readKVPair();
    readObject();
    readDoubleKVPair();
    readMultiKVPair();
    readDoubleKVObject();
    readKVPairStringValue();
    readKVPairObjectValue();
    readNestedObjects();
    readCommaList();
    readQuotedCommaList();
    readArray();
    readMultiElementArray();
    readNestedArrays();
    readMultiNestedArrays();
    readMixed();
    readMixedWithSpaces();
  }

  private void skipWhiteSpace() {
    JsonReader reader = new JsonReader(" x", new FakeJsonDelegate());
    reader.skipWhiteSpace();
    Asserts.assertTrue(reader.getCurrentCharacter() == 'x');
  }

  private void skipNoWhiteSpace() {
    JsonReader reader = new JsonReader("x", new FakeJsonDelegate());
    reader.skipWhiteSpace();
    Asserts.assertTrue(reader.getCurrentCharacter() == 'x');
  }

  private void readQuotedString() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"aaa\":42", listener);
    reader.readQuotedString();
    Asserts.assertEquals(List.of("aaa"), listener.getQuotedStrings());
    Asserts.assertEquals(':', reader.getCurrentCharacter());
  }

  private void readBareword() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("42", listener);
    reader.readBareword();
    Asserts.assertEquals(List.of("42"), listener.getBarewords());
  }

  private void readKVPair() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"aaa\":42", listener);
    reader.readKVPair();
    Asserts.assertEquals(List.of("aaa"), listener.getQuotedStrings());
    Asserts.assertEquals(List.of("42"), listener.getBarewords());
  }

  private void readObject() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("{\"aaa\":42}", listener);
    reader.readObject();
    Asserts.assertEquals(1, listener.getObjectStartCount());
    Asserts.assertEquals(List.of("aaa"), listener.getQuotedStrings());
    Asserts.assertEquals(List.of("42"), listener.getBarewords());
    Asserts.assertEquals(1, listener.getObjectEndCount());
  }

  private void readDoubleKVPair() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"aaa\":42,\"bbb\":99", listener);
    reader.readKVPairs();
    Asserts.assertEquals(List.of("aaa", "bbb"), listener.getQuotedStrings());
    Asserts.assertEquals(List.of("42", "99"), listener.getBarewords());
  }

  private void readMultiKVPair() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 26; i++) {
      if (i > 0) sb.append(',');
      sb.append('"').append((char) ('a' + i)).append('"').append(':').append(i);
    }
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader(sb.toString(), listener);
    reader.readKVPairs();
    Asserts.assertTrue(listener.containsKey("a"));
    Asserts.assertTrue(listener.containsKey("z"));
    Asserts.assertTrue(listener.containsBareword("0"));
    Asserts.assertTrue(listener.containsBareword("25"));
  }

  private void readDoubleKVObject() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("{\"aaa\":42,\"bbb\":99}", listener);
    reader.readObject();
    Asserts.assertEquals(List.of("aaa", "bbb"), listener.getQuotedStrings());
    Asserts.assertEquals(List.of("42", "99"), listener.getBarewords());
  }

  private void readKVPairStringValue() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"foo\":\"bar\"", listener);
    reader.readKVPair();
    Asserts.assertEquals(List.of("foo", "bar"), listener.getQuotedStrings());
  }

  private void readKVPairObjectValue() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"foo\":{\"bar\":42}", listener);
    reader.readKVPair();
    Asserts.assertEquals(List.of("foo", "bar"), listener.getQuotedStrings());
    Asserts.assertEquals(List.of("42"), listener.getBarewords());
    Asserts.assertEquals(1, listener.getObjectStartCount());
    Asserts.assertEquals(1, listener.getObjectEndCount());
  }

  private void readNestedObjects() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("{\"foo\":{\"bar\":42}}", listener);
    reader.readObject();
    Asserts.assertEquals(List.of("foo", "bar"), listener.getQuotedStrings());
    Asserts.assertEquals(List.of("42"), listener.getBarewords());
    Asserts.assertEquals(2, listener.getObjectStartCount());
    Asserts.assertEquals(2, listener.getObjectEndCount());
  }

  private void readCommaList() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("1,2,3,4", listener);
    reader.readCommaList();
    Asserts.assertEquals(List.of("1", "2", "3", "4"), listener.getBarewords());
  }

  private void readQuotedCommaList() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"a\",\"b\",\"c\"", listener);
    reader.readCommaList();
    Asserts.assertEquals(List.of("a", "b", "c"), listener.getQuotedStrings());
  }

  private void readArray() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("[42]", listener);
    reader.readArray();
    Asserts.assertEquals(List.of("42"), listener.getBarewords());
  }

  private void readMultiElementArray() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("[42,99,\"111\"]", listener);
    reader.readArray();
    Asserts.assertEquals(List.of("42", "99"), listener.getBarewords());
    Asserts.assertEquals(List.of("111"), listener.getQuotedStrings());
    Asserts.assertEquals(1, listener.getArrayStartCount());
    Asserts.assertEquals(1, listener.getArrayEndCount());
  }

  private void readNestedArrays() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("[42,99,[111]]", listener);
    reader.readArray();
    Asserts.assertEquals(2, listener.getArrayStartCount());
    Asserts.assertEquals(2, listener.getArrayEndCount());
    Asserts.assertEquals(List.of("42", "99", "111"), listener.getBarewords());
  }

  private void readMultiNestedArrays() {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("[[42,99],[111],66]", listener);
    reader.readArray();
    Asserts.assertEquals(3, listener.getArrayStartCount());
    Asserts.assertEquals(3, listener.getArrayEndCount());
    Asserts.assertEquals(List.of("42", "99", "111", "66"), listener.getBarewords());
  }

  private void readMixed() {
    String json = "[{\"foo\":42,\"bar\":[\"baz\"]},[111,222],66]";
    checkMixed(json);
  }

  private void readMixedWithSpaces() {
    String json = " [ { \"foo\" : 42 , \"bar\" : [ \"baz\" ] }\n , [\t111 , 222 ] , 66 ] ";
    checkMixed(json);
  }

  private static void checkMixed(String json) {
    FakeJsonDelegate listener = new FakeJsonDelegate();
    JsonReader reader = new JsonReader(json, listener);
    reader.readValue();
    Asserts.assertEquals(3, listener.getArrayStartCount());
    Asserts.assertEquals(3, listener.getArrayEndCount());
    Asserts.assertEquals(List.of("42", "111", "222", "66"), listener.getBarewords());
    Asserts.assertEquals(List.of("foo", "bar", "baz"), listener.getQuotedStrings());
  }

}
