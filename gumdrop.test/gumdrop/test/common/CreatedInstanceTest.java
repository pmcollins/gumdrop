package gumdrop.test.common;

import gumdrop.common.CreatedInstance;
import gumdrop.common.Creator;
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
    Creator<Y> y3Creator = new Creator<>(Y::new);
    Creator<Y> y2Creator = new Creator<>(Y::new);
    y2Creator.addMember("y", Y::setY, y3Creator);
    Creator<Y> y1Creator = new Creator<>(Y::new);
    y1Creator.addMember("y", Y::setY, y2Creator);
    Creator<Z> zCreator = new Creator<>(Z::new);
    Creator<X> xCreator = new Creator<>(X::new);
    xCreator.addMember("y", X::setY, y1Creator);
    xCreator.addMember("z", X::setZ, zCreator);
    CreatedInstance<X> xInstance = new CreatedInstance<>(xCreator);
    CreatedInstance<?> zInstance = xInstance.constructMember("z");
    TestUtil.assertNotNull(zInstance);
    CreatedInstance<?> y1Instance = xInstance.constructMember("y");
    TestUtil.assertNotNull(y1Instance);
    CreatedInstance<?> y2Instance = y1Instance.constructMember("y");
    TestUtil.assertNotNull(y2Instance);
    CreatedInstance<?> y3Instance = y2Instance.constructMember("y");
    TestUtil.assertNotNull(y3Instance);
    // TODO better exception
    TestUtil.assertThrows(() -> y3Instance.constructMember("y"), NullPointerException.class);
  }

  private void apply() {
    Creator<Name> nameCreator = new Creator<>(Name::new);
    nameCreator.addSetter("first", Name::setFirst);
    Name name = new Name();
    nameCreator.apply(name, "first", "Bilbo");
    TestUtil.assertEquals("Bilbo", name.getFirst());
  }

  private void topLevelArray() {
    Creator<Name> nameCreator = new Creator<>(Name::new);
    nameCreator.addSetter("first", Name::setFirst);
    nameCreator.addSetter("last", Name::setLast);
    Creator<List<Name>> listCreator = new Creator<>(ArrayList::new);
    listCreator.addMember("name", List::add, nameCreator);
    CreatedInstance<List<Name>> listInstance = new CreatedInstance<>(listCreator);
    CreatedInstance<?> nb1 = listInstance.constructMember("name");
    nb1.applyString("first", "foo");
    nb1.applyString("last", "bar");
    CreatedInstance<?> nb2 = listInstance.constructMember("name");
    nb2.applyString("first", "baz");
    nb2.applyString("last", "glarch");
    List<Name> list = listInstance.getObject();
    TestUtil.assertEquals(List.of(new Name("foo", "bar"), new Name("baz", "glarch")), list);
  }

  private void memberArray() {
    CreatedInstance<Room> roomInstance = new CreatedInstance<>(new RoomCreator());
    roomInstance.applyString("name", "land of 702");
    CreatedInstance<?> peopleInstance = roomInstance.constructMember("people");
    CreatedInstance<?> personInstance = peopleInstance.constructMember("add");
    personInstance.applyString("age", "42");
    CreatedInstance<?> nameInstance = personInstance.constructMember("name");
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
