package gumdrop.test.json;

import gumdrop.json.*;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.NamedPerson;
import gumdrop.test.fake.Person;
import gumdrop.test.util.Test;

import java.util.*;

import static gumdrop.test.util.Asserts.*;

public class JsonNodeTest extends Test {

  public static void main(String[] args) throws Exception {
    new JsonNodeTest().run();
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
    Integer integer = Json.deserialize(new IntDeserializer(), "null");
    assertNull(integer);
  }

  private void int42() {
    Integer integer = Json.deserialize(new IntDeserializer(), "42");
    assertEquals(42, integer);
  }

  private void nullArray() {
    List<Integer> list = Json.deserialize(new IntListDeserializer(), "null");
    assertNull(list);
  }

  private void emptyArray() {
    List<Integer> list = Json.deserialize(new IntListDeserializer(), "[]");
    assertListEquals(List.of(), list);
  }

  private void singleElementArray() {
    List<Integer> list = Json.deserialize(new IntListDeserializer(), "[42]");
    assertListEquals(List.of(42), list);
  }

  private void multiElementArray() {
    List<Integer> list = Json.deserialize(new IntListDeserializer(), "[42, 111]");
    assertListEquals(List.of(42, 111), list);
  }

  private void nullElementArray() {
    List<Integer> list = Json.deserialize(new IntListDeserializer(), "[null]");
    assertListEquals(Collections.singletonList(null), list);
  }

  private void intListListNodeTests() {
    IntListListDeserializer deserializer = new IntListListDeserializer();
    intListListNodeTests(deserializer);
  }

  private void intListListNodeTests(Deserializer<List<List<Integer>>> deserializer) {
    nullNestedIntArray(deserializer);
    nestedIntNullArray(deserializer);
    nestedIntEmptyArray(deserializer);
    singleNestedIntArray(deserializer);
    doubleNestedIntArray(deserializer);
    doubleDoubleNestedIntArray(deserializer);
  }

  private void nullNestedIntArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = Json.deserialize(deserializer, "[null]");
    assertListEquals(Collections.singletonList(null), list);
  }

  private void nestedIntNullArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = Json.deserialize(deserializer, "null");
    assertNull(list);
  }

  private void nestedIntEmptyArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = Json.deserialize(deserializer, "[[]]");
    assertListEquals(List.of(List.of()), list);
  }

  private void singleNestedIntArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = Json.deserialize(deserializer, "[[42]]");
    assertListEquals(List.of(List.of(42)), list);
  }

  private void doubleNestedIntArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = Json.deserialize(deserializer, "[[42], [111]]");
    assertListEquals(List.of(List.of(42), List.of(111)), list);
  }

  private void doubleDoubleNestedIntArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = Json.deserialize(deserializer, "[[42, 43], [111, 112]]");
    assertListEquals(List.of(List.of(42, 43), List.of(111, 112)), list);
  }

  private void stringIntMapTests() {
    Deserializer<Map<String, Integer>> deserializer = new StringIntMapDeserializer();
    stringIntMapTests(deserializer);
  }

  private void stringIntMapTests(Deserializer<Map<String, Integer>> deserializer) {
    nullStringIntMap(deserializer);
    emptyStringIntMap(deserializer);
    singleStringIntMap(deserializer);
    doubleStringIntMap(deserializer);
  }

  private void nullStringIntMap(Deserializer<Map<String, Integer>> deserializer) {
    Map<String, Integer> map = Json.deserialize(deserializer, "null");
    assertNull(map);
  }

  private void emptyStringIntMap(Deserializer<Map<String, Integer>> deserializer) {
    Map<String, Integer> map = Json.deserialize(deserializer, "{}");
    assertEquals(Map.of(), map);
  }

  private void singleStringIntMap(Deserializer<Map<String, Integer>> deserializer) {
    Map<String, Integer> map = Json.deserialize(deserializer, "{\"foo\":42}");
    assertEquals(Map.of("foo", 42), map);
  }

  private void doubleStringIntMap(Deserializer<Map<String, Integer>> deserializer) {
    JsonDelegate d = new StandardJsonDelegate(deserializer);
    new JsonParser(d, "{\"foo\":42,\"bar\":111}").readValue();
    Map<String, Integer> map = deserializer.getValue();
    assertEquals(Map.of("foo", 42, "bar", 111), map);
  }

  private void nullMapOfList() {
    Map<String, List<Integer>> map = Json.deserialize(new ListMapDeserializer(), "null");
    assertNull(map);
  }

  private void emptyMapOfList() {
    Map<String, List<Integer>> map = Json.deserialize(new ListMapDeserializer(), "{}");
    assertEquals(Map.of(), map);
  }

  private void nullMapValue() {
    Map<String, List<Integer>> map = Json.deserialize(new ListMapDeserializer(), "{\"foo\":null}");
    assertEquals(1, map.size());
    assertNull(map.get("foo"));
  }

  private void emptyMapValue() {
    Map<String, List<Integer>> map = Json.deserialize(new ListMapDeserializer(), "{\"foo\":[]}");
    assertEquals(1, map.size());
    assertEquals(List.of(), map.get("foo"));
  }

  private void mapMultiValue() {
    ListMapDeserializer deserializer = new ListMapDeserializer();
    String json = "{\"foo\":[],\"bar\":[42]}";
    Map<String, List<Integer>> map = Json.deserialize(deserializer, json);
    assertEquals(2, map.size());
    assertEquals(List.of(), map.get("foo"));
    assertEquals(List.of(42), map.get("bar"));
  }

  private void listOfMap() {
    ListOfMapDeserializer deserializer = new ListOfMapDeserializer();
    List<Map<String, Integer>> list = Json.deserialize(deserializer, "[{}]");
    assertEquals(List.of(Map.of()), list);
  }

  private void listOfNullMap() {
    ListOfMapDeserializer deserializer = new ListOfMapDeserializer();
    List<Map<String, Integer>> list = Json.deserialize(deserializer, "[null]");
    assertEquals(Collections.singletonList(null), list);
  }

  private void listOfTwoMaps() {
    ListOfMapDeserializer deserializer = new ListOfMapDeserializer();
    String json = "[{\"aaa\":111},{\"bbb\":222}]";
    List<Map<String, Integer>> list = Json.deserialize(deserializer, json);
    assertEquals(List.of(Map.of("aaa", 111), Map.of("bbb", 222)), list);
  }

  private void simpleNullPojo() {
    PersonDeserializer deserializer = new PersonDeserializer();
    Person person = Json.deserialize(deserializer, "null");
    assertNull(person);
  }

  private void simpleNoParamsPojo() {
    PersonDeserializer deserializer = new PersonDeserializer();
    Person person = Json.deserialize(deserializer, "{}");
    assertEquals(new Person(), person);
  }

  private void simplePojoWithExplicitNulls() {
    PersonDeserializer deserializer = new PersonDeserializer();
    String json = "{\"first\":null,\"last\":null}";
    Person person = Json.deserialize(deserializer, json);
    assertEquals(new Person(), person);
  }

  private void simplePojo() {
    PersonDeserializer deserializer = new PersonDeserializer();
    String json = "{\"first\":\"aaa\",\"last\":\"bbb\"}";
    Person person = Json.deserialize(deserializer, json);
    assertEquals(new Person("aaa", "bbb"), person);
  }

  private void complexPojo() {
    NamedPersonDeserializer deserializer = new NamedPersonDeserializer();
    complexPojo(deserializer);
  }

  private void complexPojo(Deserializer<NamedPerson> deserializer) {
    String json = "{\"age\":42,\"name\":{\"first\":\"aaa\",\"last\":\"bbb\"}}";
    NamedPerson person = Json.deserialize(deserializer, json);
    NamedPerson expected = new NamedPerson();
    expected.setAge(42);
    expected.setName(new Name("aaa", "bbb"));
    assertEquals(expected, person);
  }

  private void listNodeofIntList() {
    ListDeserializer<List<Integer>> deserializer = new ListDeserializer<>(IntListDeserializer::new);
    intListListNodeTests(deserializer);
  }

  private void listNodeOfInt() {
    String json = "[1, 2, 3]";
    ListDeserializer<Integer> deserializer = new ListDeserializer<>(IntDeserializer::new);
    List<Integer> list = Json.deserialize(deserializer, json);
    assertListEquals(List.of(1, 2, 3), list);
  }

  private void mapNode() {
    MapDeserializer<Integer> deserializer = new MapDeserializer<>(IntDeserializer::new);
    stringIntMapTests(deserializer);
  }

  private void namePojoNode() {
    PojoDeserializer<Name> deserializer = new NamePojoDeserializer();
    String json = "{\"first\":\"aaa\",\"last\":\"bbb\"}";
    Name name = Json.deserialize(deserializer, json);
    assertEquals(new Name("aaa", "bbb"), name);
  }

  private void namedPersonPojoNode() {
    complexPojo(new NamedPersonPojoDeserializer());
  }

}
