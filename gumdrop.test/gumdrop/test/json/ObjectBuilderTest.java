package gumdrop.test.json;

import gumdrop.json.ObjectBuilder;
import gumdrop.json.Setters;
import gumdrop.test.Test;
import gumdrop.test.TestUtil;

import java.util.ArrayList;
import java.util.List;

class ObjectBuilderTest extends Test {

  public static void main(String[] args) {
    new ObjectBuilderTest().run();
  }

  @Override
  public void run() {
    simple();
    apply();
    topLevelArray();
    memberArray();
  }

  private void simple() {
    Setters<Y> y3Setters = new Setters<>(Y::new);
    Setters<Y> y2Setters = new Setters<>(Y::new);
    y2Setters.addMember("y", Y::setY, y3Setters);
    Setters<Y> y1Setters = new Setters<>(Y::new);
    y1Setters.addMember("y", Y::setY, y2Setters);
    Setters<Z> zSetters = new Setters<>(Z::new);
    Setters<X> xSetters = new Setters<>(X::new);
    xSetters.addMember("y", X::setY, y1Setters);
    xSetters.addMember("z", X::setZ, zSetters);
    ObjectBuilder<X> xBuilder = new ObjectBuilder<>(xSetters);
    ObjectBuilder<?> zBuilder = xBuilder.constructMember("z");
    TestUtil.assertNotNull(zBuilder);
    ObjectBuilder<?> y1Builder = xBuilder.constructMember("y");
    TestUtil.assertNotNull(y1Builder);
    ObjectBuilder<?> y2Builder = y1Builder.constructMember("y");
    TestUtil.assertNotNull(y2Builder);
    ObjectBuilder<?> y3Builder = y2Builder.constructMember("y");
    TestUtil.assertNotNull(y3Builder);
    // TODO better exception
    TestUtil.assertThrows(() -> y3Builder.constructMember("y"), NullPointerException.class);
  }

  private void apply() {
    Setters<Name> nameSetters = new Setters<>(Name::new);
    nameSetters.addSetter("first", Name::setFirst);
    Name name = new Name();
    nameSetters.apply(name, "first", "bobo");
    TestUtil.assertEquals("bobo", name.getFirst());
  }

  private void topLevelArray() {
    Setters<Name> nameSetters = new Setters<>(Name::new);
    nameSetters.addSetter("first", Name::setFirst);
    nameSetters.addSetter("last", Name::setLast);
    Setters<List<Name>> listSetters = new Setters<>(ArrayList::new);
    listSetters.addMember("name", List::add, nameSetters);
    ObjectBuilder<List<Name>> listBuilder = new ObjectBuilder<>(listSetters);
    ObjectBuilder<?> nb1 = listBuilder.constructMember("name");
    nb1.applyString("first", "foo");
    nb1.applyString("last", "bar");
    ObjectBuilder<?> nb2 = listBuilder.constructMember("name");
    nb2.applyString("first", "baz");
    nb2.applyString("last", "glarch");
    List<Name> list = listBuilder.getObject();
    TestUtil.assertEquals(List.of(new Name("foo", "bar"), new Name("baz", "glarch")), list);
  }

  private void memberArray() {
    ObjectBuilder<Room> roomBuilder = new ObjectBuilder<>(new RoomSetters());
    roomBuilder.applyString("name", "land of 702");
    ObjectBuilder<?> peopleBuilder = roomBuilder.constructMember("people");
    ObjectBuilder<?> personBuilder = peopleBuilder.constructMember("add");
    personBuilder.applyString("age", "42");
    ObjectBuilder<?> nameBuilder = personBuilder.constructMember("name");
    nameBuilder.applyString("first", "pablo");
    nameBuilder.applyString("last", "collins");
    Room room = roomBuilder.getObject();
    TestUtil.assertEquals("land of 702", room.getName());
    List<FullNamePerson> people = room.getPeople();
    TestUtil.assertEquals(1, people.size());
    FullNamePerson person = people.get(0);
    TestUtil.assertEquals(42, person.getAge());
    Name name = person.getName();
    TestUtil.assertEquals("pablo", name.getFirst());
    TestUtil.assertEquals("collins", name.getLast());
  }

  private static class X {
    private Y y;
    private Z z;
    void setY(Y y) {
      this.y = y;
    }
    void setZ(Z z) {
      this.z = z;
    }
    @Override
    public String toString() {
      return "X{y=" + y + ", z=" + z + '}';
    }
  }

  private static class Y {
    private Y y;
    private String name;
    void setY(Y y) {
      this.y = y;
    }
    void setName(String name) {
      this.name = name;
    }
    @Override
    public String toString() {
      return "Y{y=" + y + "', name=" + name + '}';
    }
  }

  private static class Z {
  }

}
