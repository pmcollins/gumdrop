package gumdrop.test.json.v3;

import gumdrop.json.v2.*;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.NamedPerson;
import gumdrop.test.fake.Person;
import gumdrop.test.util.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static gumdrop.test.util.Asserts.*;

public class JsonV3Test extends Test {

  public static void main(String[] args) throws Exception {
    new JsonV3Test().run();
  }

  @Override
  public void run() throws Exception {
    integer();
  }

  private void integer() {
    nullInt();
    int42();
    nullArray();
    emptyArray();
    singleElementArray();
    multiElementArray();
    nullElementArray();

    intListListNodeTests();

    stringIntMapTests();

    nullMapOfList();
    emptyMapOfList();
    nullMapValue();
    emptyMapValue();
    mapMultiValue();
    listOfMap();
    listOfNullMap();
    listOfTwoMaps();
    simpleNullPojo();
    simpleNoParamsPojo();
    simplePojoWithExplicitNulls();
    simplePojo();
    complexPojo();
    listNode();
    mapNode();
    namePojoNode();
    namedPersonPojoNode();
  }

  private void nullInt() {
    IntNode n = new IntNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    Integer integer = n.getValue();
    assertNull(integer);
  }

  private void int42() {
    IntNode n = new IntNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "42").readValue();
    Integer integer = n.getValue();
    assertEquals(42, integer);
  }

  private void nullArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    assertNull(n.getValue());
  }

  private void emptyArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[]").readValue();
    assertListEquals(List.of(), n.getValue());
  }

  private void singleElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[42]").readValue();
    assertListEquals(List.of(42), n.getValue());
  }

  private void multiElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[42, 111]").readValue();
    assertListEquals(List.of(42, 111), n.getValue());
  }

  private void nullElementArray() {
    IntListNode n = new IntListNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[null]").readValue();
    assertListEquals(Collections.singletonList(null), n.getValue());
  }

  private void intListListNodeTests() {
    IntListListNode n = new IntListListNode();
    intListListNodeTests(n);
  }

  private void intListListNodeTests(Node<List<List<Integer>>> n) {
    nullNestedIntArray(n);
    nestedIntNullArray(n);
    nestedIntEmptyArray(n);
    singleNestedIntArray(n);
    doubleNestedIntArray(n);
    doubleDoubleNestedIntArray(n);
  }

  private void nullNestedIntArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[null]").readValue();
    assertListEquals(Collections.singletonList(null), n.getValue());
  }

  private void nestedIntNullArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    assertNull(n.getValue());
  }

  private void nestedIntEmptyArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[]]").readValue();
    assertListEquals(List.of(List.of()), n.getValue());
  }

  private void singleNestedIntArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42]]").readValue();
    assertListEquals(List.of(List.of(42)), n.getValue());
  }

  private void doubleNestedIntArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42], [111]]").readValue();
    assertListEquals(List.of(List.of(42), List.of(111)), n.getValue());
  }

  private void doubleDoubleNestedIntArray(Node<List<List<Integer>>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[[42, 43], [111, 112]]").readValue();
    assertListEquals(List.of(List.of(42, 43), List.of(111, 112)), n.getValue());
  }

  private void stringIntMapTests() {
    Node<Map<String, Integer>> n = new StringIntMapNode();
    stringIntMapTests(n);
  }

  private void stringIntMapTests(Node<Map<String, Integer>> n) {
    nullStringIntMap(n);
    emptyStringIntMap(n);
    singleStringIntMap(n);
    doubleStringIntMap(n);
  }

  private void nullStringIntMap(Node<Map<String, Integer>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    assertNull(n.getValue());
  }

  private void emptyStringIntMap(Node<Map<String, Integer>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{}").readValue();
    assertEquals(Map.of(), n.getValue());
  }

  private void singleStringIntMap(Node<Map<String, Integer>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":42}").readValue();
    Map<String, Integer> map = n.getValue();
    assertEquals(Map.of("foo", 42), map);
  }

  private void doubleStringIntMap(Node<Map<String, Integer>> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":42,\"bar\":111}").readValue();
    Map<String, Integer> map = n.getValue();
    assertEquals(Map.of("foo", 42, "bar", 111), map);
  }

  private void nullMapOfList() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertNull(map);
  }

  private void emptyMapOfList() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(Map.of(), map);
  }

  private void nullMapValue() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":null}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(1, map.size());
    assertNull(map.get("foo"));
  }

  private void emptyMapValue() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":[]}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(1, map.size());
    assertEquals(List.of(), map.get("foo"));
  }

  private void mapMultiValue() {
    ListMapNode n = new ListMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"foo\":[],\"bar\":[42]}").readValue();
    Map<String, List<Integer>> map = n.getValue();
    assertEquals(2, map.size());
    assertEquals(List.of(), map.get("foo"));
    assertEquals(List.of(42), map.get("bar"));
  }

  private void listOfMap() {
    ListOfMapNode n = new ListOfMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[{}]").readValue();
    List<Map<String, Integer>> list = n.getValue();
    assertEquals(List.of(Map.of()), list);
  }

  private void listOfNullMap() {
    ListOfMapNode n = new ListOfMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[null]").readValue();
    List<Map<String, Integer>> list = n.getValue();
    assertEquals(Collections.singletonList(null), list);
  }

  private void listOfTwoMaps() {
    ListOfMapNode n = new ListOfMapNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "[{\"aaa\":111},{\"bbb\":222}]").readValue();
    List<Map<String, Integer>> list = n.getValue();
    assertEquals(List.of(Map.of("aaa", 111), Map.of("bbb", 222)), list);
  }

  private void simpleNullPojo() {
    PersonNode n = new PersonNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "null").readValue();
    Person person = n.getValue();
    assertNull(person);
  }

  private void simpleNoParamsPojo() {
    PersonNode n = new PersonNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{}").readValue();
    Person person = n.getValue();
    assertEquals(new Person(), person);
  }

  private void simplePojoWithExplicitNulls() {
    PersonNode n = new PersonNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"first\":null,\"last\":null}").readValue();
    Person person = n.getValue();
    assertEquals(new Person(), person);
  }

  private void simplePojo() {
    PersonNode n = new PersonNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"first\":\"aaa\",\"last\":\"bbb\"}").readValue();
    Person person = n.getValue();
    assertEquals(new Person("aaa", "bbb"), person);
  }

  private void complexPojo() {
    NamedPersonNode n = new NamedPersonNode();
    complexPojo(n);
  }

  private void complexPojo(Node<NamedPerson> n) {
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"age\":42,\"name\":{\"first\":\"aaa\",\"last\":\"bbb\"}}").readValue();
    NamedPerson person = n.getValue();
    NamedPerson expected = new NamedPerson();
    expected.setAge(42);
    expected.setName(new Name("aaa", "bbb"));
    assertEquals(expected, person);
  }

  private void listNode() {
    ListNode<List<Integer>> n = new ListNode<>(IntListNode::new);
    intListListNodeTests(n);
  }

  private void mapNode() {
    MapNode<Integer> n = new MapNode<>(IntNode::new);
    stringIntMapTests(n);
  }

  private void namePojoNode() {
    PojoNode<Name> n = new NamePojoNode();
    JsonDelegate d = new StandardJsonDelegate(n);
    new JsonParser(d, "{\"first\":\"aaa\",\"last\":\"bbb\"}").readValue();
    Name name = n.getValue();
    assertEquals(new Name("aaa", "bbb"), name);
  }

  private void namedPersonPojoNode() {
    complexPojo(new NamedPersonPojoNode());
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class NamedPersonPojoNode extends PojoNode<NamedPerson> {

  NamedPersonPojoNode() {
    super(NamedPerson::new, List.of(
      new FieldBinding<>("age", NamedPerson::setAge, IntNode::new),
      new FieldBinding<>("name", NamedPerson::setName, NamePojoNode::new)
    ));
  }

}

class NamePojoNode extends PojoNode<Name> {

  NamePojoNode() {
    this(null);
  }

  NamePojoNode(Consumer<Name> listener) {
    super(Name::new, List.of(
      new FieldBinding<>("first", Name::setFirst, StringNode::new),
      new FieldBinding<>("last", Name::setLast, StringNode::new)
    ), listener);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class NameAttributesNode extends Node<Name> {

  NameAttributesNode(Name name) {
    super(name);
  }

  @Override
  public Chainable next(String key) {
    if ("first".equals(key)) {
      return new StringNode(s -> getValue().setFirst(s));
    } else if ("last".equals(key)) {
      return new StringNode(s -> getValue().setLast(s));
    }
    throw new UnknownKeyException(key);
  }

}

class NameNode extends NullableNode<Name> {

  NameNode(Consumer<Name> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    Name name = new Name();
    setValue(name);
    return new NameAttributesNode(name);
  }

}

class NamedPersonAttributesNode extends Node<NamedPerson> {

  NamedPersonAttributesNode(NamedPerson namedPerson) {
    super(namedPerson);
  }

  @Override
  public Chainable next(String key) {
    if ("age".equals(key)) {
      return new IntNode(age -> getValue().setAge(age));
    } else if ("name".equals(key)) {
      return new NameNode(name -> getValue().setName(name));
    }
    throw new UnknownKeyException(key);
  }

}

class NamedPersonNode extends NullableNode<NamedPerson> {

  @Override
  public Chainable next() {
    NamedPerson namedPerson = new NamedPerson();
    setValue(namedPerson);
    return new NamedPersonAttributesNode(namedPerson);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class PersonAttributesNode extends Node<Person> {

  PersonAttributesNode(Person person) {
    super(person);
  }

  @Override
  public Chainable next(String key) {
    return new StringNode(s -> {
      if ("first".equals(key)) {
        getValue().setFirst(s);
      } else if ("last".equals(key)) {
        getValue().setLast(s);
      }
    });
  }

}

class PersonNode extends NullableNode<Person> {

  @Override
  public Chainable next() {
    Person person = new Person();
    setValue(person);
    return new PersonAttributesNode(person);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class ListOfMapElementNode extends Node<List<Map<String, Integer>>> {

  ListOfMapElementNode(List<Map<String, Integer>> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new StringIntMapNode(map -> getValue().add(map));
  }

}

class ListOfMapNode extends Node<List<Map<String, Integer>>> {

  @Override
  public Chainable next() {
    ArrayList<Map<String, Integer>> list = new ArrayList<>();
    setValue(list);
    return new ListOfMapElementNode(list);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class ListMapElementNode extends Node<Map<String, List<Integer>>> {

  ListMapElementNode(Map<String, List<Integer>> map) {
    super(map);
  }

  @Override
  public Chainable next(String key) {
    return new IntListNode(list -> getValue().put(key, list));
  }

}

class ListMapNode extends NullableNode<Map<String, List<Integer>>> {

  @Override
  public Chainable next() {
    Map<String, List<Integer>> map = new HashMap<>();
    setValue(map);
    return new ListMapElementNode(map);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class StringIntMapElementNode extends Node<Map<String, Integer>> {

  StringIntMapElementNode(Map<String, Integer> map) {
    super(map);
  }

  @Override
  public Chainable next(String key) {
    return new IntNode(i -> getValue().put(key, i));
  }

}

class StringIntMapNode extends NullableNode<Map<String, Integer>> {

  StringIntMapNode() {
  }

  StringIntMapNode(Consumer<Map<String, Integer>> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    HashMap<String, Integer> value = new HashMap<>();
    setValue(value);
    return new StringIntMapElementNode(value);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class IntListListElementNode extends Node<List<List<Integer>>> {

  IntListListElementNode(List<List<Integer>> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new IntListNode(list -> {
      List<List<Integer>> value = getValue();
      value.add(list);
    });
  }

}

class IntListListNode extends NullableNode<List<List<Integer>>> {

  @Override
  public Chainable next() {
    List<List<Integer>> list = new ArrayList<>();
    setValue(list);
    return new IntListListElementNode(list);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class IntListElementNode extends Node<List<Integer>> implements Consumer<Integer> {

  IntListElementNode(List<Integer> list) {
    super(list);
  }

  @Override
  public Chainable next() {
    return new IntNode(this);
  }

  @Override
  public void accept(Integer integer) {
    getValue().add(integer);
  }

}

class IntListNode extends NullableNode<List<Integer>> {

  IntListNode() {
  }

  IntListNode(Consumer<List<Integer>> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    List<Integer> list = new ArrayList<>();
    setValue(list);
    return new IntListElementNode(list);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class NullableNode<T> extends Node<T> {

  NullableNode() {
  }

  NullableNode(Consumer<T> listener) {
    super(listener);
  }

  @Override
  public final void acceptBareword(String bareword) {
    if ("null".equals(bareword)) {
      setValue(null);
    } else {
      acceptNonNullBareword(bareword);
    }
  }

  void acceptNonNullBareword(String bareword) {
    super.acceptBareword(bareword);
  }

}

class Node<T> extends BaseChainable {

  private T value;
  private Consumer<T> listener;

  Node() {
  }

  Node(T value) {
    this.value = value;
  }

  Node(Consumer<T> listener) {
    this.listener = listener;
  }

  T getValue() {
    return value;
  }

  void setValue(T value) {
    if (listener != null) {
      listener.accept(value);
    }
    this.value = value;
  }

}

class StringNode extends NullableNode<String> {

  StringNode(Consumer<String> listener) {
    super(listener);
  }

  @Override
  public void acceptString(String value) {
    setValue(value);
  }

}

class IntNode extends NullableNode<Integer> {

  IntNode() {
  }

  IntNode(Consumer<Integer> listener) {
    super(listener);
  }

  @Override
  void acceptNonNullBareword(String bareword) {
    setValue(Integer.valueOf(bareword));
  }

}

class ListElementNode<T> extends Node<List<T>> {

  private final Function<Consumer<T>, Node<T>> nodeConstructor;

  ListElementNode(List<T> list, Function<Consumer<T>, Node<T>> nodeConstructor) {
    super(list);
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    return nodeConstructor.apply(list -> {
      List<T> value = getValue();
      value.add(list);
    });
  }

}

class ListNode<T> extends NullableNode<List<T>> {

  private final Function<Consumer<T>, Node<T>> nodeConstructor;

  ListNode(Function<Consumer<T>, Node<T>> nodeConstructor) {
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    List<T> list = new ArrayList<>();
    setValue(list);
    return new ListElementNode<>(list, nodeConstructor);
  }

}

class MapElementNode<T> extends Node<Map<String, T>> {

  private final Function<Consumer<T>, Node<T>> constructor;

  MapElementNode(Map<String, T> map, Function<Consumer<T>, Node<T>> constructor) {
    super(map);
    this.constructor = constructor;
  }

  @Override
  public Chainable next(String key) {
    return constructor.apply(t -> getValue().put(key, t));
  }

}

class MapNode<T> extends NullableNode<Map<String, T>> {

  private final Function<Consumer<T>, Node<T>> nodeConstructor;

  MapNode(Function<Consumer<T>, Node<T>> nodeConstructor) {
    this.nodeConstructor = nodeConstructor;
  }

  @Override
  public Chainable next() {
    Map<String, T> map = new HashMap<>();
    setValue(map);
    return new MapElementNode<>(map, nodeConstructor);
  }

}

class UnknownKeyException extends RuntimeException {

  UnknownKeyException(String message) {
    super(message);
  }

}

class FieldBinding<T, U> {

  private final String name;
  private final BiConsumer<T, U> setter;
  private final Function<Consumer<U>, Node<U>> nodeConstructor;

  FieldBinding(String name, BiConsumer<T, U> setter, Function<Consumer<U>, Node<U>> nodeConstructor) {
    this.name = name;
    this.nodeConstructor = nodeConstructor;
    this.setter = setter;
  }

  String getName() {
    return name;
  }

  Node<U> buildNode(T t) {
    return nodeConstructor.apply(value -> setter.accept(t, value));
  }

}

class PojoAttributesNode<T> extends Node<T> {

  private final Map<String, FieldBinding<T, ?>> map = new HashMap<>();

  PojoAttributesNode(T t, List<FieldBinding<T, ?>> bindings) {
    super(t);
    for (FieldBinding<T, ?> binding : bindings) {
      map.put(binding.getName(), binding);
    }
  }

  @Override
  public Chainable next(String key) {
    FieldBinding<T, ?> binding = map.get(key);
    return binding.buildNode(getValue());
  }

}

class PojoNode<T> extends NullableNode<T> {

  private final Supplier<T> constructor;
  private final List<FieldBinding<T, ?>> bindings;

  PojoNode(Supplier<T> constructor, List<FieldBinding<T, ?>> bindings) {
    this(constructor, bindings, null);
  }

  PojoNode(Supplier<T> constructor, List<FieldBinding<T, ?>> bindings, Consumer<T> listener) {
    super(listener);
    this.constructor = constructor;
    this.bindings = bindings;
  }

  @Override
  public Chainable next() {
    T t = constructor.get();
    setValue(t);
    return new PojoAttributesNode<>(t, bindings);
  }

}
