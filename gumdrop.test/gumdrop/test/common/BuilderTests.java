package gumdrop.test.common;

import gumdrop.common.builder.v1.Builder;
import gumdrop.common.builder.v1.BuilderNode;
import gumdrop.test.fake.RoomBuilder;
import gumdrop.test.fake.NamedPerson;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.Room;
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
    y2Builder.addBuilder("y", Y::setY, y3Builder);
    Builder<Y> y1Builder = new Builder<>(Y::new);
    y1Builder.addBuilder("y", Y::setY, y2Builder);
    Builder<Z> zBuilder = new Builder<>(Z::new);
    Builder<X> xBuilder = new Builder<>(X::new);
    xBuilder.addBuilder("y", X::setY, y1Builder);
    xBuilder.addBuilder("z", X::setZ, zBuilder);

    BuilderNode<X> xNode = new BuilderNode<>(xBuilder);
    BuilderNode<?> zNode = xNode.create("z");
    assertNotNull(zNode);
    BuilderNode<?> y1Node = xNode.create("y");
    assertNotNull(y1Node);
    BuilderNode<?> y2Node = y1Node.create("y");
    assertNotNull(y2Node);
    BuilderNode<?> y3Node = y2Node.create("y");
    assertNotNull(y3Node);
    // TODO better exception
    Asserts.assertThrows(() -> y3Node.create("y"), NullPointerException.class);
  }

  private void apply() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    Name name = nameBuilder.construct();
    nameBuilder.apply(name, "first", "Bilbo");
    assertEquals("Bilbo", name.getFirst());
  }

  private void topLevelArray() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    nameBuilder.addSetter("last", Name::setLast);
    Builder<List<Name>> listBuilder = new Builder<>(ArrayList::new);
    listBuilder.addBuilder("name", List::add, nameBuilder);
    BuilderNode<List<Name>> listNode = new BuilderNode<>(listBuilder);
    BuilderNode<?> nb1 = listNode.create("name");
    nb1.applyString("first", "foo");
    nb1.applyString("last", "bar");
    BuilderNode<?> nb2 = listNode.create("name");
    nb2.applyString("first", "baz");
    nb2.applyString("last", "glarch");
    List<Name> list = listNode.getObject();
    assertEquals(List.of(new Name("foo", "bar"), new Name("baz", "glarch")), list);
  }

  private void memberArray() {
    BuilderNode<Room> roomNode = new BuilderNode<>(new RoomBuilder());
    roomNode.applyString("name", "702");
    BuilderNode<?> peopleNode = roomNode.create("people");
    BuilderNode<?> personNode = peopleNode.create("add");
    personNode.applyString("age", "42");
    BuilderNode<?> nameNode = personNode.create("name");
    nameNode.applyString("first", "pablo");
    nameNode.applyString("last", "collins");
    Room room = roomNode.getObject();
    assertEquals("702", room.getName());
    List<NamedPerson> people = room.getPeople();
    assertEquals(1, people.size());
    NamedPerson namedPerson = people.get(0);
    assertEquals(42, namedPerson.getAge());
    Name name = namedPerson.getName();
    assertEquals("pablo", name.getFirst());
    assertEquals("collins", name.getLast());
  }

  private void map() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    nameBuilder.addSetter("last", Name::setLast);

    Builder<Map<String, Name>> mapBuilder = new Builder<>(HashMap::new);
    mapBuilder.addMapBuilder("name", Map::put, nameBuilder);

    BuilderNode<Map<String, Name>> builderNode = new BuilderNode<>(mapBuilder);

    BuilderNode<?> nameBuilderNode = builderNode.create("name");
    assertNotNull(nameBuilderNode);

    nameBuilderNode.applyString("first", "foo");
    nameBuilderNode.applyString("last", "bar");

    Map<String, Name> map = builderNode.getObject();
    assertEquals("foo", map.get("name").getFirst());
  }

  private void wildcardMap() {
    Builder<Name> nameBuilder = new Builder<>(Name::new);
    nameBuilder.addSetter("first", Name::setFirst);
    nameBuilder.addSetter("last", Name::setLast);

    Builder<Map<String, Name>> mapBuilder = new Builder<>(HashMap::new);
    mapBuilder.addMapBuilder("*", Map::put, nameBuilder);

    BuilderNode<Map<String, Name>> builderNode = new BuilderNode<>(mapBuilder);

    BuilderNode<?> nameBuilderNode = builderNode.create("baz");
    assertNotNull(nameBuilderNode);

    nameBuilderNode.applyString("first", "foo");
    nameBuilderNode.applyString("last", "bar");

    Map<String, Name> map = builderNode.getObject();
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
