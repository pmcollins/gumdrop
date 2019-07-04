package gumdrop.test.json.v2;

import gumdrop.json.v2.*;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.NamedPerson;
import gumdrop.test.fake.Person;
import gumdrop.test.fake.SimpleRoom;
import gumdrop.test.util.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertListEquals;

public class JsonV2Test extends Test {

  public static void main(String[] args) throws Exception {
    new JsonV2Test().run();
  }

  @Override
  public void run() throws Exception {
    stringArray();
    stringArrayPrinter();

    intArray();
    intArrayParser();
    intArrayDelegate();
    intArrayPrinter();

    stringStringMap();
    stringStringMapDelegate();
    stringStringMapParser();
    stringStringMapPrinter();

    arrayOfArrays();
    arrayOfArraysPrinter();

    arrayOfMaps();
    arrayOfMapsDelegate();
    arrayOfMapsPrinter();

    mapOfArrays();
    mapOfArraysDelegate();
    mapOfArraysPrinter();

    personPrinter();

    arrayOfPeople();
    arrayOfPeopleDelegate();
    arrayOfPeoplePrinter();

    simpleRoom();
    simpleRoomDelegate();

    arrayOfSimpleRooms();
    arrayOfSimpleRoomsDelegate();

    namedPerson();
    namedPersonDelegate();
    namedPersonParser();
  }

  private static void stringArray() {
    Node<List<String>> node = new StringArrayListNodeFactory().get();
    node.next().accept("a");

    List<String> list = node.instance();
    assertStringList(list);
  }

  private static void intArray() {
    IntListNode node = new IntListNode();
    node.next().accept("1");
    node.next().accept("2");

    assertIntArray(node.instance());
  }

  private static void stringArrayPrinter() {
    ArrayPrinter<String> p = new ArrayPrinter<>(new StringPrinter());
    StringBuilder sb = new StringBuilder();
    p.print(sb, List.of("a", "b"));
    assertEquals("[\"a\",\"b\"]", sb.toString());
  }

  private static void intArrayDelegate() {
    IntListNode node = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(node);
    d.push();

    d.push();
    d.pop("1");

    d.push();
    d.pop("2");

    d.pop();

    assertIntArray(node.instance());
  }

  private static void intArrayParser() {
    IntListNode node = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(node);
    JsonParser p = new JsonParser("[1,2]", d);
    p.readValue();
    List<Integer> integers = node.instance();
    assertIntArray(integers);
  }

  private static void intArrayPrinter() {
    ArrayPrinter<Integer> p = new ArrayPrinter<>(new IntPrinter());
    StringBuilder sb = new StringBuilder();
    p.print(sb, List.of(1, 2));
    assertEquals("[1,2]", sb.toString());
  }

  private static void stringStringMap() {
    StringMapNode node = new StringMapNode();
    node.next("key").accept("value");
    node.next("key2").accept("value2");

    assertStringStringMap(node);
  }

  private static void stringStringMapDelegate() {
    StringMapNode node = new StringMapNode();
    JsonDelegate d = new StandardJsonDelegate(node);
    d.push();

    d.push("key");
    d.pop("value");
    d.push("key2");
    d.pop("value2");

    d.pop();

    assertStringStringMap(node);
  }

  private static void stringStringMapParser() {
    StringMapNode node = new StringMapNode();
    JsonDelegate d = new StandardJsonDelegate(node);
    String json = "{\"key\":\"value\",\"key2\",\"value2\"}";
    JsonParser p = new JsonParser(json, d);
    p.readValue();
    assertStringStringMap(node);
  }

  private static void stringStringMapPrinter() {
    Map<String, String> m = new TreeMap<>();
    m.put("a", "yyy");
    m.put("b", "zzz");
    MapPrinter<String> p = new MapPrinter<>(new StringPrinter());
    StringBuilder sb = new StringBuilder();
    p.print(sb, m);
    assertEquals("{\"a\":\"yyy\",\"b\":\"zzz\"}", sb.toString());
  }

  private static void arrayOfArrays() {
    Binding<List<List<String>>, List<String>> b = new Binding<>(List::add, new StringArrayListNodeFactory());
    ArrayNode<List<List<String>>> node = new ArrayNode<>(new ArrayList<>(), b);
    node.next().next().accept("a");
    List<List<String>> list = node.instance();
    assertStringList(list.get(0));
  }

  private static void arrayOfArraysPrinter() {
    ArrayPrinter<List<String>> p = new ArrayPrinter<>(new ArrayPrinter<>(new StringPrinter()));
    StringBuilder sb = new StringBuilder();
    List<String> ary = List.of("aaa", "bbb");
    p.print(sb, List.of(ary, ary));
    assertEquals("[[\"aaa\",\"bbb\"],[\"aaa\",\"bbb\"]]", sb.toString());
  }

  private static void arrayOfMaps() {
    ListOfMapsNode node = new ListOfMapsNode();
    node.next().next("foo").accept("bar");
    node.next().next("baz").accept("glarch");

    assertArrayOfMaps(node);
  }

  private static void arrayOfMapsDelegate() {
    ListOfMapsNode node = new ListOfMapsNode();
    JsonDelegate d = new StandardJsonDelegate(node);
    d.push();

    d.push();
    d.push("foo");
    d.pop("bar");
    d.pop();

    d.push();
    d.push("baz");
    d.pop("glarch");
    d.pop();

    d.pop();

    assertArrayOfMaps(node);
  }

  private static void arrayOfMapsPrinter() {
    ArrayPrinter<Map<String, String>> p = new ArrayPrinter<>(
      new MapPrinter<>(
        new StringPrinter()
      )
    );
    StringBuilder sb = new StringBuilder();
    Map<String, String> map = Map.of("a", "xxx");
    p.print(sb, List.of(map, map));
    assertEquals(
      "[{\"a\":\"xxx\"},{\"a\":\"xxx\"}]",
      sb.toString()
    );
  }

  private static void mapOfArrays() {
    MapOfArraysNode node = new MapOfArraysNode();
    Chainable foo = node.next("foo");
    foo.next().accept("aaa");
    foo.next().accept("bbb");

    Chainable bar = node.next("bar");
    bar.next().accept("ccc");
    bar.next().accept("ddd");

    assertMapOfArrays(node);
  }

  private static void mapOfArraysDelegate() {
    MapOfArraysNode node = new MapOfArraysNode();
    JsonDelegate d = new StandardJsonDelegate(node);

    d.push();

    d.push("foo");
    d.push();
    d.pop("aaa");
    d.push();
    d.pop("bbb");
    d.pop();

    d.push("bar");
    d.push();
    d.pop("ccc");
    d.push();
    d.pop("ddd");
    d.pop();

    d.pop();

    assertMapOfArrays(node);
  }

  private static void mapOfArraysPrinter() {
    MapPrinter<List<String>> m = new MapPrinter<>(
      new ArrayPrinter<>(
        new StringPrinter()
      )
    );
    StringBuilder sb = new StringBuilder();
    m.print(sb, Map.of("foo", List.of("hello")));
    assertEquals("{\"foo\":[\"hello\"]}", sb.toString());
  }

  private static void personPrinter() {
    PersonPrinter p = PersonPrinter.build();
    StringBuilder sb = new StringBuilder();
    p.print(sb, new Person("bilbo", "baggins"));
    assertEquals("{\"first\":\"bilbo\",\"last\":\"baggins\"}", sb.toString());
  }

  private static void arrayOfPeople() {
    ListOfPersonNode listnodeNode = new ListOfPersonNode();
    Chainable bilboNode = listnodeNode.next();
    bilboNode.next("first").accept("bilbo");
    bilboNode.next("last").accept("baggins");

    Chainable gandalfNode = listnodeNode.next();
    gandalfNode.next("first").accept("gandalf");
    gandalfNode.next("last").accept("the grey");

    assertArrayOfPeople(listnodeNode);
  }

  private static void arrayOfPeopleDelegate() {
    ListOfPersonNode node = new ListOfPersonNode();
    JsonDelegate d = new StandardJsonDelegate(node);

    d.push();

    d.push();
    d.push("first");
    d.pop("bilbo");
    d.push("last");
    d.pop("baggins");
    d.pop();

    d.push();
    d.push("first");
    d.pop("gandalf");
    d.push("last");
    d.pop("the grey");
    d.pop();

    d.pop();

    assertArrayOfPeople(node);
  }

  private void arrayOfPeoplePrinter() {
    ArrayPrinter<Person> p = new ArrayPrinter<>(PersonPrinter.build());
    StringBuilder sb = new StringBuilder();
    p.print(sb, List.of(new Person("bilbo", "baggins")));
    assertEquals("[{\"first\":\"bilbo\",\"last\":\"baggins\"}]", sb.toString());
  }

  private static void simpleRoom() {
    SimpleRoomNode node = new SimpleRoomNode();
    Chainable pablonode = node.next();
    pablonode.next("first").accept("pablo");
    pablonode.next("last").accept("collins");

    Chainable zoeynode = node.next();
    zoeynode.next("first").accept("zoey");
    zoeynode.next("last").accept("rose");

    assertRoom(node);
  }

  private static void simpleRoomDelegate() {
    SimpleRoomNode node = new SimpleRoomNode();
    JsonDelegate d = new StandardJsonDelegate(node);

    d.push();

    d.push();
    d.push("first");
    d.pop("pablo");
    d.push("last");
    d.pop("collins");
    d.pop();
    d.push();
    d.push("first");
    d.pop("zoey");
    d.push("last");
    d.pop("rose");
    d.pop();

    d.pop();

    assertRoom(node);
  }

  private static void arrayOfSimpleRooms() {
    ListOfRoomsNode node = new ListOfRoomsNode();
    Chainable room1Node = node.next();
    Chainable person1Node = room1Node.next();
    person1Node.next("first").accept("f1");
    person1Node.next("last").accept("l1");
    Chainable person2Node = room1Node.next();
    person2Node.next("first").accept("f2");
    person2Node.next("last").accept("l2");

    Chainable room2Node = node.next();
    Chainable person3Node = room2Node.next();
    person3Node.next("first").accept("f3");
    person3Node.next("last").accept("l3");

    assertArrayOfRooms(node);
  }

  private static void arrayOfSimpleRoomsDelegate() {
    ListOfRoomsNode node = new ListOfRoomsNode();
    JsonDelegate d = new StandardJsonDelegate(node);

    d.push();

    d.push(); // room 1
    d.push(); // person 1
    d.push("first");
    d.pop("f1");
    d.push("last");
    d.pop("l1");
    d.pop();
    d.push(); // person 2
    d.push("first");
    d.pop("f2");
    d.push("last");
    d.pop("l2");
    d.pop();
    d.pop();

    d.push(); // room 2
    d.push(); // person 3
    d.push("first");
    d.pop("f3");
    d.push("last");
    d.pop("l3");
    d.pop();
    d.pop();

    d.pop();

    assertArrayOfRooms(node);
  }

  private static void namedPerson() {
    NamedPersonNode n = new NamedPersonNode();
    n.next("age").accept("111");
    Chainable holderNode = n.next("name");
    Chainable nameNode = holderNode.next();
    nameNode.next("first").accept("aaa");
    nameNode.next("last").accept("bbb");
    assertNamedPerson(n);
  }

  private static void namedPersonDelegate() {
    NamedPersonNode node = new NamedPersonNode();
    StandardJsonDelegate d = new StandardJsonDelegate(node);

    d.push();
    d.push("age");
    d.pop("111");
    d.push("name");
    d.push();
    d.push("first");
    d.pop("aaa");
    d.push("last");
    d.pop("bbb");
    d.pop();
    d.pop();
    d.pop();

    assertNamedPerson(node);
  }

  private static void namedPersonParser() {
    String json = "{\"age\":111,\"name\":{\"first\":\"aaa\",\"last\":\"bbb\"}}";
    NamedPersonNode node = new NamedPersonNode();
    JsonDelegate d = new StandardJsonDelegate(node);
    JsonParser parser = new JsonParser(json, d);
    parser.readValue();
    assertNamedPerson(node);
  }

  //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//
  //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//

  private static void assertStringList(List<String> list) {
    assertListEquals(List.of("a"), list);
  }

  private static void assertNamedPerson(NamedPersonNode n) {
    NamedPerson p = n.instance();
    assertEquals(111, p.getAge());
    Name name = p.getName();
    assertEquals("aaa", name.getFirst());
    assertEquals("bbb", name.getLast());
  }

  private static void assertArrayOfMaps(ListOfMapsNode node) {
    List<Map<String, String>> list = node.instance();
    assertEquals(2, list.size());
    assertEquals("bar", list.get(0).get("foo"));
    assertEquals("glarch", list.get(1).get("baz"));
  }

  private static void assertMapOfArrays(MapOfArraysNode node) {
    Map<String, List<String>> map = node.instance();
    List<String> fooList = map.get("foo");
    assertEquals("aaa", fooList.get(0));
    assertEquals("bbb", fooList.get(1));

    List<String> barList = map.get("bar");
    assertEquals("ccc", barList.get(0));
    assertEquals("ddd", barList.get(1));
  }

  private static void assertIntArray(List<Integer> list) {
    assertListEquals(List.of(1, 2), list);
  }

  private static void assertStringStringMap(StringMapNode node) {
    Map<String, String> map = node.instance();
    assertEquals("value", map.get("key"));
    assertEquals("value2", map.get("key2"));
  }

  private static void assertArrayOfPeople(ListOfPersonNode node) {
    List<Person> list = node.instance();
    assertEquals(2, list.size());
    Person bilbo = list.get(0);
    assertEquals("bilbo", bilbo.getFirst());
    assertEquals("baggins", bilbo.getLast());
    Person gandalf = list.get(1);
    assertEquals("gandalf", gandalf.getFirst());
    assertEquals("the grey", gandalf.getLast());
  }

  private static void assertRoom(SimpleRoomNode node) {
    SimpleRoom simpleRoom = node.instance();
    List<Person> people = simpleRoom.getPeople();
    assertEquals(2, people.size());
    Person pablo = people.get(0);
    assertEquals("pablo", pablo.getFirst());
    assertEquals("collins", pablo.getLast());
    Person zoey = people.get(1);
    assertEquals("zoey", zoey.getFirst());
    assertEquals("rose", zoey.getLast());
  }

  private static void assertArrayOfRooms(ListOfRoomsNode node) {
    List<SimpleRoom> simpleRooms = node.instance();
    assertEquals(2, simpleRooms.size());
    SimpleRoom simpleRoom1 = simpleRooms.get(0);
    List<Person> people1 = simpleRoom1.getPeople();
    assertEquals(2, people1.size());
    Person person1 = people1.get(0);
    assertEquals("f1", person1.getFirst());
    assertEquals("l1", person1.getLast());
    Person person2 = people1.get(1);
    assertEquals("f2", person2.getFirst());
    assertEquals("l2", person2.getLast());
    SimpleRoom simpleRoom2 = simpleRooms.get(1);
    List<Person> people2 = simpleRoom2.getPeople();
    assertEquals(1, people2.size());
    Person person3 = people2.get(0);
    assertEquals("f3", person3.getFirst());
    assertEquals("l3", person3.getLast());
  }

}
