package gumdrop.test.json.v2;

import gumdrop.json.v2.*;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.NamedPerson;
import gumdrop.test.fake.Person;
import gumdrop.test.util.Test;

import java.util.*;

import static gumdrop.test.util.Asserts.*;

public class JsonV2NodeTest extends Test {

  public static void main(String[] args) throws Exception {
    new JsonV2NodeTest().run();
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
    intListListNodeTests();
    stringIntMapTests();
    nullMapOfList();
    emptyMapOfList();
    nullMapValue();
    emptyMapValue();
    mapMultiValue();
    listOfMap();
    listOfNullMap();
    listOfTwoMaps();
    simpleNullPojo();
    simpleNoParamsPojo();
    simplePojoWithExplicitNulls();
    simplePojo();
    complexPojo();
    listNodeofIntList();
    listNodeOfInt();
    mapNode();
    namePojoNode();
    namedPersonPojoNode();
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
    assertListEquals(List.of(), n.getValue());
  }

  private void singleElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[42]").readValue();
    assertListEquals(List.of(42), n.getValue());
  }

  private void multiElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[42, 111]").readValue();
    assertListEquals(List.of(42, 111), n.getValue());
  }

  private void nullElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[null]").readValue();
    assertListEquals(Collections.singletonList(null), n.getValue());
  }

  private void intListListNodeTests() {
    IntListListNode n = new IntListListNode();
    intListListNodeTests(n);
  }

  private void intListListNodeTests(Node<List<List<Integer>>> n) {
    nullNestedIntArray(n);
    nestedIntNullArray(n);
    nestedIntEmptyArray(n);
    singleNestedIntArray(n);
    doubleNestedIntArray(n);
    doubleDoubleNestedIntArray(n);
  }

  private void nullNestedIntArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[null]").readValue();
    assertListEquals(Collections.singletonList(null), n.getValue());
  }

  private void nestedIntNullArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    assertNull(n.getValue());
  }

  private void nestedIntEmptyArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[]]").readValue();
    assertListEquals(List.of(List.of()), n.getValue());
  }

  private void singleNestedIntArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42]]").readValue();
    assertListEquals(List.of(List.of(42)), n.getValue());
  }

  private void doubleNestedIntArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42], [111]]").readValue();
    assertListEquals(List.of(List.of(42), List.of(111)), n.getValue());
  }

  private void doubleDoubleNestedIntArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42, 43], [111, 112]]").readValue();
    assertListEquals(List.of(List.of(42, 43), List.of(111, 112)), n.getValue());
  }

  private void stringIntMapTests() {
    Node<Map<String, Integer>> n = new StringIntMapNode();
    stringIntMapTests(n);
  }

  private void stringIntMapTests(Node<Map<String, Integer>> n) {
    nullStringIntMap(n);
    emptyStringIntMap(n);
    singleStringIntMap(n);
    doubleStringIntMap(n);
  }

  private void nullStringIntMap(Node<Map<String, Integer>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    assertNull(n.getValue());
  }

  private void emptyStringIntMap(Node<Map<String, Integer>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{}").readValue();
    assertEquals(Map.of(), n.getValue());
  }

  private void singleStringIntMap(Node<Map<String, Integer>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":42}").readValue();
    Map<String, Integer> map = n.getValue();
    assertEquals(Map.of("foo", 42), map);
  }

  private void doubleStringIntMap(Node<Map<String, Integer>> n) {
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
    assertEquals(Map.of(), map);
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
    assertEquals(List.of(), map.get("foo"));
  }

  private void mapMultiValue() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":[],\"bar\":[42]}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(2, map.size());
    assertEquals(List.of(), map.get("foo"));
    assertEquals(List.of(42), map.get("bar"));
  }

  private void listOfMap() {
    ListOfMapNode n = new ListOfMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[{}]").readValue();
    List<Map<String, Integer>> list = n.getValue();
    assertEquals(List.of(Map.of()), list);
  }

  private void listOfNullMap() {
    ListOfMapNode n = new ListOfMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[null]").readValue();
    List<Map<String, Integer>> list = n.getValue();
    assertEquals(Collections.singletonList(null), list);
  }

  private void listOfTwoMaps() {
    ListOfMapNode n = new ListOfMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[{\"aaa\":111},{\"bbb\":222}]").readValue();
    List<Map<String, Integer>> list = n.getValue();
    assertEquals(List.of(Map.of("aaa", 111), Map.of("bbb", 222)), list);
  }

  private void simpleNullPojo() {
    PersonNode n = new PersonNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    Person person = n.getValue();
    assertNull(person);
  }

  private void simpleNoParamsPojo() {
    PersonNode n = new PersonNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{}").readValue();
    Person person = n.getValue();
    assertEquals(new Person(), person);
  }

  private void simplePojoWithExplicitNulls() {
    PersonNode n = new PersonNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"first\":null,\"last\":null}").readValue();
    Person person = n.getValue();
    assertEquals(new Person(), person);
  }

  private void simplePojo() {
    PersonNode n = new PersonNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"first\":\"aaa\",\"last\":\"bbb\"}").readValue();
    Person person = n.getValue();
    assertEquals(new Person("aaa", "bbb"), person);
  }

  private void complexPojo() {
    NamedPersonNode n = new NamedPersonNode();
    complexPojo(n);
  }

  private void complexPojo(Node<NamedPerson> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"age\":42,\"name\":{\"first\":\"aaa\",\"last\":\"bbb\"}}").readValue();
    NamedPerson person = n.getValue();
    NamedPerson expected = new NamedPerson();
    expected.setAge(42);
    expected.setName(new Name("aaa", "bbb"));
    assertEquals(expected, person);
  }

  private void listNodeofIntList() {
    ListNode<List<Integer>> n = new ListNode<>(IntListNode::new);
    intListListNodeTests(n);
  }

  private void listNodeOfInt() {
    ListNode<Integer> n = new ListNode<>(IntNode::new);
    new JsonParser(new StandardJsonDelegate(n), "[1, 2, 3]").readValue();
    List<Integer> list = n.getValue();
    assertListEquals(List.of(1, 2, 3), list);
  }

  private void mapNode() {
    MapNode<Integer> n = new MapNode<>(IntNode::new);
    stringIntMapTests(n);
  }

  private void namePojoNode() {
    PojoNode<Name> n = new NamePojoNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"first\":\"aaa\",\"last\":\"bbb\"}").readValue();
    Name name = n.getValue();
    assertEquals(new Name("aaa", "bbb"), name);
  }

  private void namedPersonPojoNode() {
    complexPojo(new NamedPersonPojoNode());
  }

}
