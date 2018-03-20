package gumdrop.test.common;

import gumdrop.common.builder.Builder;
import gumdrop.common.builder.InstanceBuilder;
import gumdrop.test.json.RoomBuilder;
import gumdrop.test.pojo.FullNamePerson;
import gumdrop.test.pojo.Name;
import gumdrop.test.pojo.Room;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertNotNull;

class BuilderTests extends Test {

  public static void main(String[] args) {
    new BuilderTests().run();
  }

  @Override
  public void run() {
    simple();
    apply();
    topLevelArray();
    memberArray();
    map();
    wildcardMap();
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

    InstanceBuilder<X> xInstance = new InstanceBuilder<>(xBuilder);
    InstanceBuilder<?> zInstance = xInstance.constructAndSet("z");
    assertNotNull(zInstance);
    InstanceBuilder<?> y1Instance = xInstance.constructAndSet("y");
    assertNotNull(y1Instance);
    InstanceBuilder<?> y2Instance = y1Instance.constructAndSet("y");
    assertNotNull(y2Instance);
    InstanceBuilder<?> y3Instance = y2Instance.constructAndSet("y");
    assertNotNull(y3Instance);
    // TODO better exception
    Asserts.assertThrows(() -> y3Instance.constructAndSet("y"), NullPointerException.class);
  }

  private void apply() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    Name name = new Name();
    nameBuilder.apply(name, "first", "Bilbo");
    assertEquals("Bilbo", name.getFirst());
  }

  private void topLevelArray() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    nameBuilder.addSetter("last", Name::setLast);
    Builder<List<Name>> listBuilder = new Builder<>(ArrayList::new);
    listBuilder.addMember("name", List::add, nameBuilder);
    InstanceBuilder<List<Name>> listInstance = new InstanceBuilder<>(listBuilder);
    InstanceBuilder<?> nb1 = listInstance.constructAndSet("name");
    nb1.applyString("first", "foo");
    nb1.applyString("last", "bar");
    InstanceBuilder<?> nb2 = listInstance.constructAndSet("name");
    nb2.applyString("first", "baz");
    nb2.applyString("last", "glarch");
    List<Name> list = listInstance.getObject();
    assertEquals(List.of(new Name("foo", "bar"), new Name("baz", "glarch")), list);
  }

  private void memberArray() {
    InstanceBuilder<Room> roomInstance = new InstanceBuilder<>(new RoomBuilder());
    roomInstance.applyString("name", "land of 702");
    InstanceBuilder<?> peopleInstance = roomInstance.constructAndSet("people");
    InstanceBuilder<?> personInstance = peopleInstance.constructAndSet("add");
    personInstance.applyString("age", "42");
    InstanceBuilder<?> nameInstance = personInstance.constructAndSet("name");
    nameInstance.applyString("first", "pablo");
    nameInstance.applyString("last", "collins");
    Room room = roomInstance.getObject();
    assertEquals("land of 702", room.getName());
    List<FullNamePerson> people = room.getPeople();
    assertEquals(1, people.size());
    FullNamePerson person = people.get(0);
    assertEquals(42, person.getAge());
    Name name = person.getName();
    assertEquals("pablo", name.getFirst());
    assertEquals("collins", name.getLast());
  }

  private void map() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    nameBuilder.addSetter("last", Name::setLast);

    Builder<Map<String, Name>> mapBuilder = new Builder<>(HashMap::new);
    mapBuilder.addMapMember("name", Map::put, nameBuilder);

    InstanceBuilder<Map<String, Name>> instanceBuilder = new InstanceBuilder<>(mapBuilder);

    InstanceBuilder<?> nameInstanceBuilder = instanceBuilder.constructAndSet("name");
    assertNotNull(nameInstanceBuilder);

    nameInstanceBuilder.applyString("first", "foo");
    nameInstanceBuilder.applyString("last", "bar");

    Map<String, Name> map = instanceBuilder.getObject();
    assertEquals("foo", map.get("name").getFirst());
  }

  private void wildcardMap() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    nameBuilder.addSetter("last", Name::setLast);

    Builder<Map<String, Name>> mapBuilder = new Builder<>(HashMap::new);
    mapBuilder.addMapMember("*", Map::put, nameBuilder);

    InstanceBuilder<Map<String, Name>> instanceBuilder = new InstanceBuilder<>(mapBuilder);

    InstanceBuilder<?> nameInstanceBuilder = instanceBuilder.constructAndSet("baz");
    assertNotNull(nameInstanceBuilder);

    nameInstanceBuilder.applyString("first", "foo");
    nameInstanceBuilder.applyString("last", "bar");

    Map<String, Name> map = instanceBuilder.getObject();
    assertEquals("foo", map.get("baz").getFirst());
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
