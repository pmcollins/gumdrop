package gumdrop.test.json;

import gumdrop.json.JsonReader;
import gumdrop.test.fake.FakeJsonDelegate;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import java.util.List;

import static gumdrop.test.util.Asserts.assertEquals;

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
    emptyObject();
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
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"aaa\":42", delegate);
    reader.readQuotedString();
    assertEquals(List.of("aaa"), delegate.getQuotedStrings());
    assertEquals(':', reader.getCurrentCharacter());
  }

  private void readBareword() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("42", delegate);
    reader.readBareword();
    assertEquals(List.of("42"), delegate.getBarewords());
  }

  private void readKVPair() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"aaa\":42", delegate);
    reader.readKVPair();
    assertEquals(List.of("aaa"), delegate.getQuotedStrings());
    assertEquals(List.of("42"), delegate.getBarewords());
  }

  private void readObject() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("{\"aaa\":42}", delegate);
    reader.readObject();
    assertEquals(1, delegate.getObjectStartCount());
    assertEquals(List.of("aaa"), delegate.getQuotedStrings());
    assertEquals(List.of("42"), delegate.getBarewords());
    assertEquals(1, delegate.getObjectEndCount());
  }

  private void readDoubleKVPair() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"aaa\":42,\"bbb\":99", delegate);
    reader.readKVPairs();
    assertEquals(List.of("aaa", "bbb"), delegate.getQuotedStrings());
    assertEquals(List.of("42", "99"), delegate.getBarewords());
  }

  private void readMultiKVPair() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 26; i++) {
      if (i > 0) sb.append(',');
      sb.append('"').append((char) ('a' + i)).append('"').append(':').append(i);
    }
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader(sb.toString(), delegate);
    reader.readKVPairs();
    Asserts.assertTrue(delegate.containsKey("a"));
    Asserts.assertTrue(delegate.containsKey("z"));
    Asserts.assertTrue(delegate.containsBareword("0"));
    Asserts.assertTrue(delegate.containsBareword("25"));
  }

  private void readDoubleKVObject() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("{\"aaa\":42,\"bbb\":99}", delegate);
    reader.readObject();
    assertEquals(List.of("aaa", "bbb"), delegate.getQuotedStrings());
    assertEquals(List.of("42", "99"), delegate.getBarewords());
  }

  private void readKVPairStringValue() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"foo\":\"bar\"", delegate);
    reader.readKVPair();
    assertEquals(List.of("foo", "bar"), delegate.getQuotedStrings());
  }

  private void readKVPairObjectValue() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"foo\":{\"bar\":42}", delegate);
    reader.readKVPair();
    assertEquals(List.of("foo", "bar"), delegate.getQuotedStrings());
    assertEquals(List.of("42"), delegate.getBarewords());
    assertEquals(1, delegate.getObjectStartCount());
    assertEquals(1, delegate.getObjectEndCount());
  }

  private void readNestedObjects() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("{\"foo\":{\"bar\":42}}", delegate);
    reader.readObject();
    assertEquals(List.of("foo", "bar"), delegate.getQuotedStrings());
    assertEquals(List.of("42"), delegate.getBarewords());
    assertEquals(2, delegate.getObjectStartCount());
    assertEquals(2, delegate.getObjectEndCount());
  }

  private void readCommaList() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("1,2,3,4", delegate);
    reader.readCommaList();
    assertEquals(List.of("1", "2", "3", "4"), delegate.getBarewords());
  }

  private void readQuotedCommaList() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("\"a\",\"b\",\"c\"", delegate);
    reader.readCommaList();
    assertEquals(List.of("a", "b", "c"), delegate.getQuotedStrings());
  }

  private void readArray() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("[42]", delegate);
    reader.readArray();
    assertEquals(List.of("42"), delegate.getBarewords());
  }

  private void readMultiElementArray() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("[42,99,\"111\"]", delegate);
    reader.readArray();
    assertEquals(List.of("42", "99"), delegate.getBarewords());
    assertEquals(List.of("111"), delegate.getQuotedStrings());
    assertEquals(1, delegate.getArrayStartCount());
    assertEquals(1, delegate.getArrayEndCount());
  }

  private void readNestedArrays() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("[42,99,[111]]", delegate);
    reader.readArray();
    assertEquals(2, delegate.getArrayStartCount());
    assertEquals(2, delegate.getArrayEndCount());
    assertEquals(List.of("42", "99", "111"), delegate.getBarewords());
  }

  private void readMultiNestedArrays() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("[[42,99],[111],66]", delegate);
    reader.readArray();
    assertEquals(3, delegate.getArrayStartCount());
    assertEquals(3, delegate.getArrayEndCount());
    assertEquals(List.of("42", "99", "111", "66"), delegate.getBarewords());
  }

  private void readMixed() {
    String json = "[{\"foo\":42,\"bar\":[\"baz\"]},[111,222],66]";
    checkMixed(json);
  }

  private void readMixedWithSpaces() {
    String json = " [ { \"foo\" : 42 , \"bar\" : [ \"baz\" ] }\n" +
      " , [\t111 , 222 ] , 66 ] ";
    checkMixed(json);
  }

  private static void checkMixed(String json) {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader(json, delegate);
    reader.readValue();
    assertEquals(3, delegate.getArrayStartCount());
    assertEquals(3, delegate.getArrayEndCount());
    assertEquals(List.of("42", "111", "222", "66"), delegate.getBarewords());
    assertEquals(List.of("foo", "bar", "baz"), delegate.getQuotedStrings());
  }

  private void emptyObject() {
    FakeJsonDelegate delegate = new FakeJsonDelegate();
    JsonReader reader = new JsonReader("{}", delegate);
    reader.readValue();
    assertEquals(1, delegate.getObjectStartCount());
    assertEquals(1, delegate.getObjectEndCount());
  }

}
