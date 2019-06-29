package gumdrop.test.json.v2;

import gumdrop.json.v2.*;
import gumdrop.json.v2.common.Node;
import gumdrop.test.util.Asserts;
import gumdrop.test.util.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonV2Test extends Test {

  public static void main(String[] args) throws Exception {
    new JsonV2Test().run();
  }

  @Override
  public void run() throws Exception {
    intArray();
    intArrayParser();
    intArrayDelegate();

    stringArray();

    stringStringMap();
    stringStringMapDelegate();
    stringStringMapParser();

    arrayOfArrays();

    arrayOfMaps();
    arrayOfMapsDelegate();

    mapOfArrays();
    mapOfArraysDelegate();

    arrayOfPeople();
    arrayOfPeopleDelegate();

    room();
    roomDelegate();

    arrayOfRooms();
    arrayOfRoomsDelegate();
  }

  private static void intArray() {
    IntListNode node = new IntListNode();
    node.next().accept("1");
    node.next().accept("2");

    assertIntArray(node.get());
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

    assertIntArray(node.get());
  }

  private static void intArrayParser() {
    IntListNode node = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(node);
    JsonParser p = new JsonParser("[1,2]", d);
    p.readValue();
    List<Integer> integers = node.get();
    assertIntArray(integers);
  }

  private static void stringArray() {
    StringArrayListNode node = new StringArrayListNode();
    node.next().accept("a");

    List<String> list = node.get();
    assertStringList(list);
  }

  private static void assertStringList(List<String> list) {
    Asserts.assertListEquals(List.of("a"), list);
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

  private static void arrayOfArrays() {
    Binding<List<List<String>>, List<String>> b = new Binding<>(
      List::add, StringArrayListNode::new
    );
    ArrayNode<List<List<String>>> node = new ArrayNode<>(new ArrayList<>(), b);
    node.next().next().accept("a");
    List<List<String>> list = node.get();
    assertStringList(list.get(0));
  }

  private static void stringStringMapParser() {
    StringMapNode node = new StringMapNode();
    JsonDelegate d = new StandardJsonDelegate(node);
    JsonParser p = new JsonParser(
      "{\"key\":\"value\",\"key2\",\"value2\"}",
      d
    );
    p.readValue();
    assertStringStringMap(node);
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

  private static void mapOfArrays() {
    MapOfArraysNode node = new MapOfArraysNode();
    Node foo = node.next("foo");
    foo.next().accept("aaa");
    foo.next().accept("bbb");

    Node bar = node.next("bar");
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

  private static void arrayOfPeople() {
    ListOfPersonNode listnodeNode = new ListOfPersonNode();
    Node bilboNode = listnodeNode.next();
    bilboNode.next("first").accept("bilbo");
    bilboNode.next("last").accept("baggins");

    Node gandalfNode = listnodeNode.next();
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

  private static void room() {
    RoomNode node = new RoomNode();
    Node pablonode = node.next();
    pablonode.next("first").accept("pablo");
    pablonode.next("last").accept("collins");

    Node zoeynode = node.next();
    zoeynode.next("first").accept("zoey");
    zoeynode.next("last").accept("rose");

    assertRoom(node);
  }

  private static void roomDelegate() {
    RoomNode node = new RoomNode();
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

  private static void arrayOfRooms() {
    ListOfRoomsNode node = new ListOfRoomsNode();
    Node room1node = node.next();
    Node person1node = room1node.next();
    person1node.next("first").accept("f1");
    person1node.next("last").accept("l1");
    Node person2node = room1node.next();
    person2node.next("first").accept("f2");
    person2node.next("last").accept("l2");

    Node room2node = node.next();
    Node person3node = room2node.next();
    person3node.next("first").accept("f3");
    person3node.next("last").accept("l3");

    assertArrayOfRooms(node);
  }

  private static void arrayOfRoomsDelegate() {
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

  /*______*/

  private static void assertArrayOfMaps(ListOfMapsNode node) {
    List<Map<String, String>> list = node.get();
    Asserts.assertEquals(2, list.size());
    Asserts.assertEquals("bar", list.get(0).get("foo"));
    Asserts.assertEquals("glarch", list.get(1).get("baz"));
  }

  private static void assertMapOfArrays(MapOfArraysNode node) {
    Map<String, List<String>> map = node.get();
    List<String> fooList = map.get("foo");
    Asserts.assertEquals("aaa", fooList.get(0));
    Asserts.assertEquals("bbb", fooList.get(1));

    List<String> barList = map.get("bar");
    Asserts.assertEquals("ccc", barList.get(0));
    Asserts.assertEquals("ddd", barList.get(1));
  }

  private static void assertIntArray(List<Integer> list) {
    Asserts.assertListEquals(List.of(1, 2), list);
  }

  private static void assertStringStringMap(
    StringMapNode node
  ) {
    Map<String, String> map = node.get();
    Asserts.assertEquals("value", map.get("key"));
    Asserts.assertEquals("value2", map.get("key2"));
  }

  private static void assertArrayOfPeople(ListOfPersonNode node) {
    List<Person> list = node.get();
    Asserts.assertEquals(2, list.size());
    Person bilbo = list.get(0);
    Asserts.assertEquals("bilbo", bilbo.getFirst());
    Asserts.assertEquals("baggins", bilbo.getLast());
    Person gandalf = list.get(1);
    Asserts.assertEquals("gandalf", gandalf.getFirst());
    Asserts.assertEquals("the grey", gandalf.getLast());
  }

  private static void assertRoom(RoomNode node) {
    Room room = node.get();
    List<Person> people = room.getPeople();
    Asserts.assertEquals(2, people.size());
    Person pablo = people.get(0);
    Asserts.assertEquals("pablo", pablo.getFirst());
    Asserts.assertEquals("collins", pablo.getLast());
    Person zoey = people.get(1);
    Asserts.assertEquals("zoey", zoey.getFirst());
    Asserts.assertEquals("rose", zoey.getLast());
  }

  private static void assertArrayOfRooms(ListOfRoomsNode node) {
    List<Room> rooms = node.get();
    Asserts.assertEquals(2, rooms.size());
    Room room1 = rooms.get(0);
    List<Person> people1 = room1.getPeople();
    Asserts.assertEquals(2, people1.size());
    Person person1 = people1.get(0);
    Asserts.assertEquals("f1", person1.getFirst());
    Asserts.assertEquals("l1", person1.getLast());
    Person person2 = people1.get(1);
    Asserts.assertEquals("f2", person2.getFirst());
    Asserts.assertEquals("l2", person2.getLast());
    Room room2 = rooms.get(1);
    List<Person> people2 = room2.getPeople();
    Asserts.assertEquals(1, people2.size());
    Person person3 = people2.get(0);
    Asserts.assertEquals("f3", person3.getFirst());
    Asserts.assertEquals("l3", person3.getLast());
  }

}
