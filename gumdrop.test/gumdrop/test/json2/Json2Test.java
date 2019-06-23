package gumdrop.test.json2;

import gumdrop.json2.BuilderNode;
import gumdrop.json2.JsonDelegate;
import gumdrop.json2.StandardJsonDelegate;
import gumdrop.test.util.Asserts;
import gumdrop.test.util.Test;

import java.util.List;
import java.util.Map;

public class Json2Test extends Test {

  public static void main(String[] args) throws Exception {
    new Json2Test().run();
  }

  @Override
  public void run() throws Exception {
    intArray();
    stringArray();
    stringStringMap();
    stringStringMapDelegate();
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
    intArrayDelegate();
  }

  private static void intArray() {
    ListOfIntUniCreatorNode creator = new ListOfIntUniCreatorNode();
    creator.next().accept("1");
    creator.next().accept("2");

    assertIntArray(creator.get());
  }

  private static void intArrayDelegate() {
    ListOfIntUniCreatorNode creator = new ListOfIntUniCreatorNode();
    JsonDelegate d = new StandardJsonDelegate(creator);
    d.push();
    d.pop("1");
    d.push();
    d.pop("2");
    assertIntArray(creator.get());
  }

  private static void stringArray() {
    StringListUniCreatorNode creator = new StringListUniCreatorNode();
    creator.next().accept("a");

    List<String> list = creator.get();
    Asserts.assertListEquals(List.of("a"), list);
  }

  private static void stringStringMap() {
    StringMapKeyedCreatorNode creator = new StringMapKeyedCreatorNode();
    creator.next("key").accept("value");
    creator.next("key2").accept("value2");

    assertStringStringMap(creator);
  }

  private static void stringStringMapDelegate() {
    StringMapKeyedCreatorNode creator = new StringMapKeyedCreatorNode();
    JsonDelegate d = new StandardJsonDelegate(creator);
    d.push("key");
    d.pop("value");
    d.push("key2");
    d.pop("value2");

    assertStringStringMap(creator);
  }

  private static void arrayOfMaps() {
    ListOfMapsCreatorNode creator = new ListOfMapsCreatorNode();
    creator.next().next("foo").accept("bar");
    creator.next().next("baz").accept("glarch");

    assertArrayOfMaps(creator);
  }

  private static void arrayOfMapsDelegate() {
    ListOfMapsCreatorNode creator = new ListOfMapsCreatorNode();
    JsonDelegate d = new StandardJsonDelegate(creator);
    d.push();
    d.push("foo");
    d.pop("bar");
    d.pop();

    d.push();
    d.push("baz");
    d.pop("glarch");
    d.pop();

    assertArrayOfMaps(creator);
  }

  private static void mapOfArrays() {
    MapOfArraysCreatorNode creator = new MapOfArraysCreatorNode();
    BuilderNode foo = creator.next("foo");
    foo.next().accept("aaa");
    foo.next().accept("bbb");

    BuilderNode bar = creator.next("bar");
    bar.next().accept("ccc");
    bar.next().accept("ddd");

    assertMapOfArrays(creator);
  }

  private static void mapOfArraysDelegate() {
    MapOfArraysCreatorNode creator = new MapOfArraysCreatorNode();
    JsonDelegate d = new StandardJsonDelegate(creator);

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

    assertMapOfArrays(creator);
  }

  private static void arrayOfPeople() {
    ListOfPersonCreatorNode listCreatorNode = new ListOfPersonCreatorNode();
    BuilderNode bilboNode = listCreatorNode.next();
    bilboNode.next("first").accept("bilbo");
    bilboNode.next("last").accept("baggins");

    BuilderNode gandalfNode = listCreatorNode.next();
    gandalfNode.next("first").accept("gandalf");
    gandalfNode.next("last").accept("the grey");

    assertArrayOfPeople(listCreatorNode);
  }

  private static void arrayOfPeopleDelegate() {
    ListOfPersonCreatorNode creator = new ListOfPersonCreatorNode();
    JsonDelegate d = new StandardJsonDelegate(creator);

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

    assertArrayOfPeople(creator);
  }

  private static void room() {
    RoomCreatorNode creator = new RoomCreatorNode();
    BuilderNode pabloCreator = creator.next();
    pabloCreator.next("first").accept("pablo");
    pabloCreator.next("last").accept("collins");

    BuilderNode zoeyCreator = creator.next();
    zoeyCreator.next("first").accept("zoey");
    zoeyCreator.next("last").accept("rose");

    assertRoom(creator);
  }

  private static void roomDelegate() {
    RoomCreatorNode creator = new RoomCreatorNode();
    JsonDelegate d = new StandardJsonDelegate(creator);

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
    assertRoom(creator);
  }

  private static void arrayOfRooms() {
    ListOfRoomsCreatorNode creator = new ListOfRoomsCreatorNode();
    BuilderNode room1Creator = creator.next();
    BuilderNode person1Creator = room1Creator.next();
    person1Creator.next("first").accept("f1");
    person1Creator.next("last").accept("l1");
    BuilderNode person2Creator = room1Creator.next();
    person2Creator.next("first").accept("f2");
    person2Creator.next("last").accept("l2");

    BuilderNode room2Creator = creator.next();
    BuilderNode person3Creator = room2Creator.next();
    person3Creator.next("first").accept("f3");
    person3Creator.next("last").accept("l3");

    assertArrayOfRooms(creator);
  }

  private static void arrayOfRoomsDelegate() {
    ListOfRoomsCreatorNode creator = new ListOfRoomsCreatorNode();
    JsonDelegate d = new StandardJsonDelegate(creator);

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

    assertArrayOfRooms(creator);
  }

  /*______*/

  private static void assertArrayOfMaps(ListOfMapsCreatorNode creator) {
    List<Map<String, String>> list = creator.get();
    Asserts.assertEquals(2, list.size());
    Asserts.assertEquals("bar", list.get(0).get("foo"));
    Asserts.assertEquals("glarch", list.get(1).get("baz"));
  }

  private static void assertMapOfArrays(MapOfArraysCreatorNode creator) {
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

  private static void assertStringStringMap(StringMapKeyedCreatorNode creator) {
    Map<String, String> map = creator.get();
    Asserts.assertEquals("value", map.get("key"));
    Asserts.assertEquals("value2", map.get("key2"));
  }

  private static void assertArrayOfPeople(ListOfPersonCreatorNode creator) {
    List<Person> list = creator.get();
    Asserts.assertEquals(2, list.size());
    Person bilbo = list.get(0);
    Asserts.assertEquals("bilbo", bilbo.getFirst());
    Asserts.assertEquals("baggins", bilbo.getLast());
    Person gandalf = list.get(1);
    Asserts.assertEquals("gandalf", gandalf.getFirst());
    Asserts.assertEquals("the grey", gandalf.getLast());
  }

  private static void assertRoom(RoomCreatorNode creator) {
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

  private static void assertArrayOfRooms(ListOfRoomsCreatorNode creator) {
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
