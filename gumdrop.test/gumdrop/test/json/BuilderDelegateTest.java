package gumdrop.test.json;

import gumdrop.json.BuilderDelegate;
import gumdrop.json.JsonReader;
import gumdrop.test.fake.Person;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.Room;
import gumdrop.test.fake.RoomBuilder;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import java.util.List;

class BuilderDelegateTest extends Test {

  public static void main(String[] args) {
    new BuilderDelegateTest().run();
  }

  @Override
  public void run() {
    manual();
//    manualWithNull();
    json();
  }

  private void manual() {
    RoomBuilder roomBuilder = new RoomBuilder();
    BuilderDelegate<Room> delegate = new BuilderDelegate<>(roomBuilder);
    delegate.objectStart();
    delegate.quotedString("name");
    delegate.quotedString("703");
    delegate.quotedString("people");
    delegate.arrayStart();
    delegate.objectStart();
    delegate.quotedString("age");
    delegate.bareword("42");
    delegate.quotedString("name");
    delegate.objectStart();
    delegate.quotedString("first");
    delegate.quotedString("foo");
    delegate.quotedString("last");
    delegate.quotedString("bar");
    delegate.objectEnd();
    delegate.objectEnd();
    delegate.objectStart();
    delegate.quotedString("age");
    delegate.bareword("110");
    delegate.quotedString("name");
    delegate.objectStart();
    delegate.quotedString("first");
    delegate.quotedString("bilbo");
    delegate.quotedString("last");
    delegate.quotedString("baggins");
    delegate.objectEnd();
    delegate.objectEnd();
    delegate.arrayEnd();
    delegate.objectEnd();

    Room room = delegate.getObject();
    checkRoom(room);
  }

  private void manualWithNull() {
    RoomBuilder roomBuilder = new RoomBuilder();
    BuilderDelegate<Room> delegate = new BuilderDelegate<>(roomBuilder);
    delegate.objectStart();
    delegate.quotedString("name");
    delegate.quotedString("703");
    delegate.quotedString("people");
    delegate.bareword("null");
    delegate.objectEnd();
  }

  private void json() {
    String json = "{ \"name\" : 703, \"people\" : [ " +
      "{ \"age\" : 42,  \"name\" : { \"first\" : \"foo\",   \"last\" : \"bar\" } }, " +
      "{ \"age\" : 110, \"name\" : { \"first\" : \"bilbo\", \"last\" : \"baggins\" } } " +
    "] }";
    RoomBuilder roomCreator = new RoomBuilder();
    BuilderDelegate<Room> delegate = new BuilderDelegate<>(roomCreator);
    JsonReader jsonReader = new JsonReader(json, delegate);
    jsonReader.readValue();
    Room room = delegate.getObject();
    checkRoom(room);
  }

  private void checkRoom(Room room) {
    Asserts.assertEquals("703", room.getName());
    List<Person> people = room.getPeople();
    Asserts.assertEquals(2, people.size());

    Person p1 = people.get(0);
    Asserts.assertEquals(42, p1.getAge());
    Asserts.assertEquals(new Name("foo", "bar"), p1.getName());

    Person p2 = people.get(1);
    Asserts.assertEquals(110, p2.getAge());
    Asserts.assertEquals(new Name("bilbo", "baggins"), p2.getName());
  }

}
