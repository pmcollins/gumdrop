package gumdrop.test.common;

import gumdrop.common.Builder;
import gumdrop.common.BuilderInstance;
import gumdrop.test.*;
import gumdrop.test.pojo.FullNamePerson;
import gumdrop.test.pojo.Name;
import gumdrop.test.pojo.Room;
import gumdrop.test.util.Test;
import gumdrop.test.util.TestUtil;

import java.util.ArrayList;
import java.util.List;

class CreatedInstanceTest extends Test {

  public static void main(String[] args) {
    new CreatedInstanceTest().run();
  }

  @Override
  public void run() {
    simple();
    apply();
    topLevelArray();
    memberArray();
  }

  private void simple() {
    Builder<Y> y3Builder = new Builder<>(Y::new);
    Builder<Y> y2Builder = new Builder<>(Y::new);
    y2Builder.addMember("y", Y::setY, y3Builder);
    Builder<Y> y1Builder = new Builder<>(Y::new);
    y1Builder.addMember("y", Y::setY, y2Builder);
    Builder<Z> zBuilder = new Builder<>(Z::new);
    Builder<X> xBuilder = new Builder<>(X::new);
    xBuilder.addMember("y", X::setY, y1Builder);
    xBuilder.addMember("z", X::setZ, zBuilder);
    BuilderInstance<X> xInstance = new BuilderInstance<>(xBuilder);
    BuilderInstance<?> zInstance = xInstance.constructMember("z");
    TestUtil.assertNotNull(zInstance);
    BuilderInstance<?> y1Instance = xInstance.constructMember("y");
    TestUtil.assertNotNull(y1Instance);
    BuilderInstance<?> y2Instance = y1Instance.constructMember("y");
    TestUtil.assertNotNull(y2Instance);
    BuilderInstance<?> y3Instance = y2Instance.constructMember("y");
    TestUtil.assertNotNull(y3Instance);
    // TODO better exception
    TestUtil.assertThrows(() -> y3Instance.constructMember("y"), NullPointerException.class);
  }

  private void apply() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    Name name = new Name();
    nameBuilder.apply(name, "first", "Bilbo");
    TestUtil.assertEquals("Bilbo", name.getFirst());
  }

  private void topLevelArray() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    nameBuilder.addSetter("last", Name::setLast);
    Builder<List<Name>> listBuilder = new Builder<>(ArrayList::new);
    listBuilder.addMember("name", List::add, nameBuilder);
    BuilderInstance<List<Name>> listInstance = new BuilderInstance<>(listBuilder);
    BuilderInstance<?> nb1 = listInstance.constructMember("name");
    nb1.applyString("first", "foo");
    nb1.applyString("last", "bar");
    BuilderInstance<?> nb2 = listInstance.constructMember("name");
    nb2.applyString("first", "baz");
    nb2.applyString("last", "glarch");
    List<Name> list = listInstance.getObject();
    TestUtil.assertEquals(List.of(new Name("foo", "bar"), new Name("baz", "glarch")), list);
  }

  private void memberArray() {
    BuilderInstance<Room> roomInstance = new BuilderInstance<>(new RoomBuilder());
    roomInstance.applyString("name", "land of 702");
    BuilderInstance<?> peopleInstance = roomInstance.constructMember("people");
    BuilderInstance<?> personInstance = peopleInstance.constructMember("add");
    personInstance.applyString("age", "42");
    BuilderInstance<?> nameInstance = personInstance.constructMember("name");
    nameInstance.applyString("first", "pablo");
    nameInstance.applyString("last", "collins");
    Room room = roomInstance.getObject();
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
