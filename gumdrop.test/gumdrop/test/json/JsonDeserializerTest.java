package gumdrop.test.json;

import gumdrop.json.*;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.Person;
import gumdrop.test.util.Test;

import java.util.*;

import static gumdrop.test.util.Asserts.*;

public class JsonDeserializerTest extends Test {

  public static void main(String[] args) throws Exception {
    new JsonDeserializerTest().run();
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
    Integer integer = new IntDeserializer().toObject("null");
    assertNull(integer);
  }

  private void int42() {
    Integer integer = new IntDeserializer().toObject("42");
    assertEquals(42, integer);
  }

  private void nullArray() {
    List<Integer> list = new IntListDeserializer().toObject("null");
    assertNull(list);
  }

  private void emptyArray() {
    List<Integer> list = new IntListDeserializer().toObject("[]");
    assertListEquals(List.of(), list);
  }

  private void singleElementArray() {
    List<Integer> list = new IntListDeserializer().toObject("[42]");
    assertListEquals(List.of(42), list);
  }

  private void multiElementArray() {
    IntListDeserializer intListDeserializer = new IntListDeserializer();
    List<Integer> list = intListDeserializer.toObject("[42, 111]");
    assertListEquals(List.of(42, 111), list);
  }

  private void nullElementArray() {
    List<Integer> list = new IntListDeserializer().toObject("[null]");
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
    List<List<Integer>> list = deserializer.toObject("[null]");
    assertListEquals(Collections.singletonList(null), list);
  }

  private void nestedIntNullArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = deserializer.toObject("null");
    assertNull(list);
  }

  private void nestedIntEmptyArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = deserializer.toObject("[[]]");
    assertListEquals(List.of(List.of()), list);
  }

  private void singleNestedIntArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = deserializer.toObject("[[42]]");
    assertListEquals(List.of(List.of(42)), list);
  }

  private void doubleNestedIntArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = deserializer.toObject("[[42], [111]]");
    assertListEquals(List.of(List.of(42), List.of(111)), list);
  }

  private void doubleDoubleNestedIntArray(Deserializer<List<List<Integer>>> deserializer) {
    List<List<Integer>> list = deserializer.toObject("[[42, 43], [111, 112]]");
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
    Map<String, Integer> map = deserializer.toObject("null");
    assertNull(map);
  }

  private void emptyStringIntMap(Deserializer<Map<String, Integer>> deserializer) {
    Map<String, Integer> map = deserializer.toObject("{}");
    assertEquals(Map.of(), map);
  }

  private void singleStringIntMap(Deserializer<Map<String, Integer>> deserializer) {
    Map<String, Integer> map = deserializer.toObject("{\"foo\":42}");
    assertEquals(Map.of("foo", 42), map);
  }

  private void doubleStringIntMap(Deserializer<Map<String, Integer>> deserializer) {
    JsonDelegate d = new StandardJsonDelegate(deserializer);
    new JsonParser(d, "{\"foo\":42,\"bar\":111}").readValue();
    Map<String, Integer> map = deserializer.getValue();
    assertEquals(Map.of("foo", 42, "bar", 111), map);
  }

  private void nullMapOfList() {
    Map<String, List<Integer>> map = new ListMapDeserializer().toObject("null");
    assertNull(map);
  }

  private void emptyMapOfList() {
    Map<String, List<Integer>> map = new ListMapDeserializer().toObject("{}");
    assertEquals(Map.of(), map);
  }

  private void nullMapValue() {
    Map<String, List<Integer>> map = new ListMapDeserializer().toObject("{\"foo\":null}");
    assertEquals(1, map.size());
    assertNull(map.get("foo"));
  }

  private void emptyMapValue() {
    Map<String, List<Integer>> map = new ListMapDeserializer().toObject("{\"foo\":[]}");
    assertEquals(1, map.size());
    assertEquals(List.of(), map.get("foo"));
  }

  private void mapMultiValue() {
    ListMapDeserializer deserializer = new ListMapDeserializer();
    String json = "{\"foo\":[],\"bar\":[42]}";
    Map<String, List<Integer>> map = deserializer.toObject(json);
    assertEquals(2, map.size());
    assertEquals(List.of(), map.get("foo"));
    assertEquals(List.of(42), map.get("bar"));
  }

  private void listOfMap() {
    ListOfMapDeserializer deserializer = new ListOfMapDeserializer();
    List<Map<String, Integer>> list = deserializer.toObject("[{}]");
    assertEquals(List.of(Map.of()), list);
  }

  private void listOfNullMap() {
    ListOfMapDeserializer deserializer = new ListOfMapDeserializer();
    List<Map<String, Integer>> list = deserializer.toObject("[null]");
    assertEquals(Collections.singletonList(null), list);
  }

  private void listOfTwoMaps() {
    ListOfMapDeserializer deserializer = new ListOfMapDeserializer();
    String json = "[{\"aaa\":111},{\"bbb\":222}]";
    List<Map<String, Integer>> list = deserializer.toObject(json);
    assertEquals(List.of(Map.of("aaa", 111), Map.of("bbb", 222)), list);
  }

  private void simpleNullPojo() {
    NameDeserializer deserializer = new NameDeserializer();
    Name person = deserializer.toObject("null");
    assertNull(person);
  }

  private void simpleNoParamsPojo() {
    PersonNullableDeserializer deserializer = new PersonNullableDeserializer();
    Name person = deserializer.toObject("{}");
    assertEquals(new Name(), person);
  }

  private void simplePojoWithExplicitNulls() {
    PersonNullableDeserializer deserializer = new PersonNullableDeserializer();
    String json = "{\"first\":null,\"last\":null}";
    Name person = deserializer.toObject(json);
    assertEquals(new Name(), person);
  }

  private void simplePojo() {
    PersonNullableDeserializer deserializer = new PersonNullableDeserializer();
    String json = "{\"first\":\"aaa\",\"last\":\"bbb\"}";
    Name person = deserializer.toObject(json);
    assertEquals(new Name("aaa", "bbb"), person);
  }

  private void complexPojo() {
    NamedPersonNullableDeserializer deserializer = new NamedPersonNullableDeserializer();
    complexPojo(deserializer);
  }

  private void complexPojo(Deserializer<Person> deserializer) {
    String json = "{\"age\":42,\"name\":{\"first\":\"aaa\",\"last\":\"bbb\"}}";
    Person person = deserializer.toObject(json);
    Person expected = new Person();
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
    List<Integer> list = deserializer.toObject(json);
    assertListEquals(List.of(1, 2, 3), list);
  }

  private void mapNode() {
    MapDeserializer<Integer> deserializer = new MapDeserializer<>(IntDeserializer::new);
    stringIntMapTests(deserializer);
  }

  private void namePojoNode() {
    ObjectDeserializer<Name> deserializer = new NameDeserializer();
    String json = "{\"first\":\"Bilbo\",\"last\":\"Baggins\"}";
    Name name = deserializer.toObject(json);
    assertEquals(new Name("Bilbo", "Baggins"), name);
  }

  private void namedPersonPojoNode() {
    complexPojo(new PersonDeserializer());
  }

}
