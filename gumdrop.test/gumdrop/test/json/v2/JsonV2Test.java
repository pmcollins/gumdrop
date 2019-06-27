package gumdrop.test.json.v2;

import gumdrop.json.v2.Node;
import gumdrop.json.v2.JsonDelegate;
import gumdrop.json.v2.JsonParser;
import gumdrop.json.v2.StandardJsonDelegate;
import gumdrop.test.util.Asserts;
import gumdrop.test.util.Test;

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
    ListOfIntUniNode creator = new ListOfIntUniNode();
    creator.next().accept("1");
    creator.next().accept("2");

    assertIntArray(creator.get());
  }

  private static void intArrayDelegate() {
    ListOfIntUniNode creator = new ListOfIntUniNode();
    JsonDelegate d = new StandardJsonDelegate(creator);
    d.push();

    d.push();
    d.pop("1");

    d.push();
    d.pop("2");

    d.pop();

    assertIntArray(creator.get());
  }

  private static void intArrayParser() {
    ListOfIntUniNode creator = new ListOfIntUniNode();
    JsonDelegate d = new StandardJsonDelegate(creator);
    JsonParser p = new JsonParser("[1,2]", d);
    p.readValue();
    List<Integer> integers = creator.get();
    assertIntArray(integers);
  }

  private static void stringArray() {
    StringListUniNode creator = new StringListUniNode();
    creator.next().accept("a");

    List<String> list = creator.get();
    Asserts.assertListEquals(List.of("a"), list);
  }

  private static void stringStringMap() {
    StringMapKeyedNode creator = new StringMapKeyedNode();
    creator.next("key").accept("value");
    creator.next("key2").accept("value2");

    assertStringStringMap(creator);
  }

  private static void stringStringMapDelegate() {
    StringMapKeyedNode creator = new StringMapKeyedNode();
    JsonDelegate d = new StandardJsonDelegate(creator);
    d.push();

    d.push("key");
    d.pop("value");
    d.push("key2");
    d.pop("value2");

    d.pop();

    assertStringStringMap(creator);
  }

  private static void stringStringMapParser() {
    StringMapKeyedNode creator = new StringMapKeyedNode();
    JsonDelegate d = new StandardJsonDelegate(creator);
    JsonParser p = new JsonParser("{\"key\":\"value\",\"key2\",\"value2\"}", d);
    p.readValue();
    assertStringStringMap(creator);
  }

  private static void arrayOfMaps() {
    ListOfMapsNode creator = new ListOfMapsNode();
    creator.next().next("foo").accept("bar");
    creator.next().next("baz").accept("glarch");

    assertArrayOfMaps(creator);
  }

  private static void arrayOfMapsDelegate() {
    ListOfMapsNode creator = new ListOfMapsNode();
    JsonDelegate d = new StandardJsonDelegate(creator);
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

    assertArrayOfMaps(creator);
  }

  private static void mapOfArrays() {
    MapOfArraysNode creator = new MapOfArraysNode();
    Node foo = creator.next("foo");
    foo.next().accept("aaa");
    foo.next().accept("bbb");

    Node bar = creator.next("bar");
    bar.next().accept("ccc");
    bar.next().accept("ddd");

    assertMapOfArrays(creator);
  }

  private static void mapOfArraysDelegate() {
    MapOfArraysNode creator = new MapOfArraysNode();
    JsonDelegate d = new StandardJsonDelegate(creator);

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

    assertMapOfArrays(creator);
  }

  private static void arrayOfPeople() {
    ListOfPersonNode listCreatorNode = new ListOfPersonNode();
    Node bilboNode = listCreatorNode.next();
    bilboNode.next("first").accept("bilbo");
    bilboNode.next("last").accept("baggins");

    Node gandalfNode = listCreatorNode.next();
    gandalfNode.next("first").accept("gandalf");
    gandalfNode.next("last").accept("the grey");

    assertArrayOfPeople(listCreatorNode);
  }

  private static void arrayOfPeopleDelegate() {
    ListOfPersonNode creator = new ListOfPersonNode();
    JsonDelegate d = new StandardJsonDelegate(creator);

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

    assertArrayOfPeople(creator);
  }

  private static void room() {
    RoomNode creator = new RoomNode();
    Node pabloCreator = creator.next();
    pabloCreator.next("first").accept("pablo");
    pabloCreator.next("last").accept("collins");

    Node zoeyCreator = creator.next();
    zoeyCreator.next("first").accept("zoey");
    zoeyCreator.next("last").accept("rose");

    assertRoom(creator);
  }

  private static void roomDelegate() {
    RoomNode creator = new RoomNode();
    JsonDelegate d = new StandardJsonDelegate(creator);

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

    assertRoom(creator);
  }

  private static void arrayOfRooms() {
    ListOfRoomsNode creator = new ListOfRoomsNode();
    Node room1Creator = creator.next();
    Node person1Creator = room1Creator.next();
    person1Creator.next("first").accept("f1");
    person1Creator.next("last").accept("l1");
    Node person2Creator = room1Creator.next();
    person2Creator.next("first").accept("f2");
    person2Creator.next("last").accept("l2");

    Node room2Creator = creator.next();
    Node person3Creator = room2Creator.next();
    person3Creator.next("first").accept("f3");
    person3Creator.next("last").accept("l3");

    assertArrayOfRooms(creator);
  }

  private static void arrayOfRoomsDelegate() {
    ListOfRoomsNode creator = new ListOfRoomsNode();
    JsonDelegate d = new StandardJsonDelegate(creator);

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

    assertArrayOfRooms(creator);
  }

  /*______*/

  private static void assertArrayOfMaps(ListOfMapsNode creator) {
    List<Map<String, String>> list = creator.get();
    Asserts.assertEquals(2, list.size());
    Asserts.assertEquals("bar", list.get(0).get("foo"));
    Asserts.assertEquals("glarch", list.get(1).get("baz"));
  }

  private static void assertMapOfArrays(MapOfArraysNode creator) {
    Map<String, List<String>> map = creator.get();
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

  private static void assertStringStringMap(StringMapKeyedNode creator) {
    Map<String, String> map = creator.get();
    Asserts.assertEquals("value", map.get("key"));
    Asserts.assertEquals("value2", map.get("key2"));
  }

  private static void assertArrayOfPeople(ListOfPersonNode creator) {
    List<Person> list = creator.get();
    Asserts.assertEquals(2, list.size());
    Person bilbo = list.get(0);
    Asserts.assertEquals("bilbo", bilbo.getFirst());
    Asserts.assertEquals("baggins", bilbo.getLast());
    Person gandalf = list.get(1);
    Asserts.assertEquals("gandalf", gandalf.getFirst());
    Asserts.assertEquals("the grey", gandalf.getLast());
  }

  private static void assertRoom(RoomNode creator) {
    Room room = creator.get();
    List<Person> people = room.getPeople();
    Asserts.assertEquals(2, people.size());
    Person pablo = people.get(0);
    Asserts.assertEquals("pablo", pablo.getFirst());
    Asserts.assertEquals("collins", pablo.getLast());
    Person zoey = people.get(1);
    Asserts.assertEquals("zoey", zoey.getFirst());
    Asserts.assertEquals("rose", zoey.getLast());
  }

  private static void assertArrayOfRooms(ListOfRoomsNode creator) {
    List<Room> rooms = creator.get();
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
