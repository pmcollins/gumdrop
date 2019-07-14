package gumdrop.test.json.v3;

import gumdrop.json.v2.*;
import gumdrop.test.util.Test;

import java.util.*;
import java.util.function.Consumer;

import static gumdrop.test.util.Asserts.*;

public class JsonV3Test extends Test {

  public static void main(String[] args) throws Exception {
    new JsonV3Test().run();
  }

  @Override
  public void run() throws Exception {
    integer();
  }

  private void integer() {
    nullInt();
    int42();
    nullArray();
    emptyArray();
    singleElementArray();
    multiElementArray();
    nullElementArray();
    nullNestedIntArray();
    nestedIntNullArray();
    nestedIntEmptyArray();
    singleNestedIntArray();
    doubleNestedIntArray();
    doubleDoubleNestedIntArray();
    nullStringStringMap();
    emptyStringStringMap();
    singleStringStringMap();
    doubleStringStringMap();
    nullMapOfList();
    emptyMapOfList();
    nullMapValue();
    emptyMapValue();
    mapMultiValue();
  }

  private void nullInt() {
    IntNode n = new IntNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    Integer integer = n.getValue();
    assertNull(integer);
  }

  private void int42() {
    IntNode n = new IntNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "42").readValue();
    Integer integer = n.getValue();
    assertEquals(42, integer);
  }

  private void nullArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    assertNull(n.getValue());
  }

  private void emptyArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[]").readValue();
    assertListEquals(Collections.emptyList(), n.getValue());
  }

  private void singleElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[42]").readValue();
    assertListEquals(Collections.singletonList(42), n.getValue());
  }

  private void multiElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[42, 111]").readValue();
    assertListEquals(Arrays.asList(42, 111), n.getValue());
  }

  private void nullElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[null]").readValue();
    assertListEquals(Collections.singletonList(null), n.getValue());
  }

  private void nullNestedIntArray() {
    IntListListNode n = new IntListListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[null]").readValue();
    assertListEquals(Collections.singletonList(null), n.getValue());
  }

  private void nestedIntNullArray() {
    IntListListNode n = new IntListListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    assertNull(n.getValue());
  }

  private void nestedIntEmptyArray() {
    IntListListNode n = new IntListListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[]]").readValue();
    assertListEquals(Collections.singletonList(Collections.emptyList()), n.getValue());
  }

  private void singleNestedIntArray() {
    IntListListNode n = new IntListListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42]]").readValue();
    assertListEquals(Collections.singletonList(Collections.singletonList(42)), n.getValue());
  }

  private void doubleNestedIntArray() {
    IntListListNode n = new IntListListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42], [111]]").readValue();
    assertListEquals(Arrays.asList(Collections.singletonList(42), Collections.singletonList(111)), n.getValue());
  }

  private void doubleDoubleNestedIntArray() {
    IntListListNode n = new IntListListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42, 43], [111, 112]]").readValue();
    assertListEquals(Arrays.asList(Arrays.asList(42, 43), Arrays.asList(111, 112)), n.getValue());
  }

  private void nullStringStringMap() {
    StringStringMapNode n = new StringStringMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    assertNull(n.getValue());
  }

  private void emptyStringStringMap() {
    StringStringMapNode n = new StringStringMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{}").readValue();
    assertEquals(Collections.emptyMap(), n.getValue());
  }

  private void singleStringStringMap() {
    StringStringMapNode n = new StringStringMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":42}").readValue();
    Map<String, Integer> map = n.getValue();
    assertEquals(Map.of("foo", 42), map);
  }

  private void doubleStringStringMap() {
    StringStringMapNode n = new StringStringMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":42,\"bar\":111}").readValue();
    Map<String, Integer> map = n.getValue();
    assertEquals(Map.of("foo", 42, "bar", 111), map);
  }

  private void nullMapOfList() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertNull(map);
  }

  private void emptyMapOfList() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(Collections.emptyMap(), map);
  }

  private void nullMapValue() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":null}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(1, map.size());
    assertNull(map.get("foo"));
  }

  private void emptyMapValue() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":[]}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(1, map.size());
    assertEquals(Collections.emptyList(), map.get("foo"));
  }

  private void mapMultiValue() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":[],\"bar\":[42]}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(2, map.size());
    assertEquals(Collections.emptyList(), map.get("foo"));
    assertEquals(List.of(42), map.get("bar"));
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class ListMapElementNode extends Node<Map<String, List<Integer>>> {

  ListMapElementNode(Map<String, List<Integer>> map) {
    super(map);
  }

  @Override
  public Chainable next(String key) {
    return new IntListNode(list -> getValue().put(key, list));
  }

}

class ListMapNode extends NullableNode<Map<String, List<Integer>>> {

  @Override
  public Chainable next() {
    Map<String, List<Integer>> map = new HashMap<>();
    setValue(map);
    return new ListMapElementNode(map);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class StringStringMapElementNode extends Node<Map<String, Integer>> {

  StringStringMapElementNode(Map<String, Integer> map) {
    super(map);
  }

  @Override
  public Chainable next(String key) {
    return new IntNode(i -> getValue().put(key, i));
  }

}

class StringStringMapNode extends NullableNode<Map<String, Integer>> {

  @Override
  public Chainable next() {
    HashMap<String, Integer> value = new HashMap<>();
    setValue(value);
    return new StringStringMapElementNode(value);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class IntListListNode extends NullableNode<List<List<Integer>>> {

  @Override
  public Chainable next() {
    List<List<Integer>> list = new ArrayList<>();
    setValue(list);
    return new IntListListElementNode(list);
  }

}

class IntListListElementNode extends Node<List<List<Integer>>> implements Consumer<List<Integer>> {

  IntListListElementNode(List<List<Integer>> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new IntListNode(this);
  }

  @Override
  public void accept(List<Integer> list) {
    getValue().add(list);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class IntListElementNode extends Node<List<Integer>> implements Consumer<Integer> {

  IntListElementNode(List<Integer> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new IntNode(this);
  }

  @Override
  public void accept(Integer integer) {
    getValue().add(integer);
  }

}

class IntListNode extends NullableNode<List<Integer>> {

  IntListNode() {
  }

  IntListNode(Consumer<List<Integer>> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    List<Integer> list = new ArrayList<>();
    setValue(list);
    return new IntListElementNode(list);
  }

}

class IntNode extends NullableNode<Integer> {

  IntNode() {
  }

  IntNode(Consumer<Integer> listener) {
    super(listener);
  }

  @Override
  void acceptNonNullBareword(String bareword) {
    setValue(Integer.valueOf(bareword));
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class NullableNode<T> extends Node<T> {

  NullableNode() {
  }

  NullableNode(T value) {
    super(value);
  }

  NullableNode(Consumer<T> listener) {
    super(listener);
  }

  @Override
  public final void acceptBareword(String bareword) {
    if ("null".equals(bareword)) {
      setValue(null);
    } else {
      acceptNonNullBareword(bareword);
    }
  }

  void acceptNonNullBareword(String bareword) {
    super.acceptBareword(bareword);
  }

}

class Node<T> extends BaseChainable {

  private T value;
  private Consumer<T> listener;

  Node() {
  }

  Node(T value) {
    this.value = value;
  }

  Node(Consumer<T> listener) {
    this.listener = listener;
  }

  T getValue() {
    return value;
  }

  void setValue(T value) {
    if (listener != null) {
      listener.accept(value);
    }
    this.value = value;
  }

}
