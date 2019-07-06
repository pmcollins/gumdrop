package gumdrop.test.json.v1;

import gumdrop.json.v1.BuilderDelegate;
import gumdrop.json.v1.JsonReader;
import gumdrop.test.fake.NamedPerson;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.Room;
import gumdrop.test.fake.RoomBuilder;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import java.util.List;

import static gumdrop.test.util.Asserts.assertNull;

class BuilderDelegateTest extends Test {

  public static void main(String[] args) {
    new BuilderDelegateTest().run();
  }

  @Override
  public void run() {
    manual();
    manualWithNull();
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
    Room room = delegate.getObject();
    assertNull(room.getPeople());
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
    List<NamedPerson> people = room.getPeople();
    Asserts.assertEquals(2, people.size());

    NamedPerson p1 = people.get(0);
    Asserts.assertEquals(42, p1.getAge());
    Asserts.assertEquals(new Name("foo", "bar"), p1.getName());

    NamedPerson p2 = people.get(1);
    Asserts.assertEquals(110, p2.getAge());
    Asserts.assertEquals(new Name("bilbo", "baggins"), p2.getName());
  }

}