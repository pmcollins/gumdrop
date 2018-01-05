package gumdrop.json.test;

import gumdrop.json.BuilderDelegate;
import gumdrop.test.Test;
import gumdrop.test.TestUtil;

import java.util.List;

class BuilderDelegateTest extends Test {

  public static void main(String[] args) {
    new BuilderDelegateTest().run();
  }

  @Override
  public void run() {
    manual();
    json();
  }

  private void manual() {
    RoomSetters setters = new RoomSetters();
    BuilderDelegate<Room> delegate = new BuilderDelegate<>(setters);
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

  private void json() {
    String json = "{ \"name\" : 703, \"people\" : [ " +
      "{ \"age\" : 42,  \"name\" : { \"first\" : \"foo\",   \"last\" : \"bar\" } }, " +
      "{ \"age\" : 110, \"name\" : { \"first\" : \"bilbo\", \"last\" : \"baggins\" } } " +
    "] }";
    RoomSetters roomSetters = new RoomSetters();
    Room room = roomSetters.fromJson(json);
    checkRoom(room);
  }

  private void checkRoom(Room room) {
    TestUtil.assertEquals("703", room.getName());
    List<FullNamePerson> people = room.getPeople();
    TestUtil.assertEquals(2, people.size());

    FullNamePerson p1 = people.get(0);
    TestUtil.assertEquals(42, p1.getAge());
    TestUtil.assertEquals(new Name("foo", "bar"), p1.getName());

    FullNamePerson p2 = people.get(1);
    TestUtil.assertEquals(110, p2.getAge());
    TestUtil.assertEquals(new Name("bilbo", "baggins"), p2.getName());
  }

}
