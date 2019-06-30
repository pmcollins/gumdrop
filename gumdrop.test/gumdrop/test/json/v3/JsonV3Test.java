package gumdrop.test.json.v3;

import gumdrop.json.v2.common.StringDictionaryAcceptorNode;
import gumdrop.json.v2.TriConsumer;
import gumdrop.json.v2.common.StringAcceptorNode;
import gumdrop.json.v2.common.Chainable;
import gumdrop.json.v2.common.Node;
import gumdrop.test.util.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class JsonV3Test extends Test {

  public static void main(String[] args) throws Exception {
    new JsonV3Test().run();
  }

  @Override
  public void run() throws Exception {
    stringList();
    intList();
    stringStringMap();
    listOfLists();
    listOfMaps();
  }

  private void stringList() {
    ArrayMaker<List<String>> m = new ArrayMaker<>(ArrayList::new, List::add);
    ArrayNode<List<String>> node = new ArrayNode<>(m);
    node.next().accept("a");
    List<String> strings = node.get();
    System.out.println(strings);
  }

  private void intList() {
    ArrayMaker<List<Integer>> m = new ArrayMaker<>(
      ArrayList::new,
      (list, s) -> list.add(Integer.valueOf(s))
    );
    ArrayNode<List<Integer>> node = new ArrayNode<>(m);
    node.next().accept("1");
    List<Integer> integers = node.get();
    System.out.println(integers);
  }

  private void stringStringMap() {
    DictionaryMaker<Map<String, String>> r = new DictionaryMaker<>(HashMap::new, Map::put);
    DictionaryNode<Map<String, String>> n = new DictionaryNode<>(r);
    n.next("key").accept("value");
    n.next("key2").accept("value2");
    Map<String, String> map = n.get();
    System.out.println(map);
  }

  private void listOfLists() {
    StringArrayListMaker subMaker = new StringArrayListMaker();
    ArraySetterBinding<List<List<String>>, List<String>> b = new ArraySetterBinding<>(List::add, subMaker);
    ArraySubObjectMaker<List<List<String>>> r = new ArraySubObjectMaker<>(ArrayList::new, b);
    ArraySubObjectNode<List<List<String>>> node = new ArraySubObjectNode<>(r);
    node.next().next().accept("zzz");
    List<List<String>> list = node.get();
    System.out.println(list);
  }

  private void listOfMaps() {
    StringStringDictionaryMaker subMaker = new StringStringDictionaryMaker();
    DictionarySetterBinding<List<Map<String, String>>, Map<String, String>> b = new DictionarySetterBinding<>(List::add, subMaker);
    DictionarySubObjectMaker<List<Map<String, String>>> m = new DictionarySubObjectMaker<>(ArrayList::new, b);

  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class StringArrayListMaker extends ArrayMaker<List<String>> {
  StringArrayListMaker() {
    super(ArrayList::new, List::add);
  }
}

class StringStringDictionaryMaker extends DictionaryMaker<Map<String, String>> {
  StringStringDictionaryMaker() {
    super(HashMap::new, Map::put);
  }
}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class ArrayMaker<T> {

  final Supplier<T> constructor;
  final BiConsumer<T, String> method;

  ArrayMaker(Supplier<T> constructor, BiConsumer<T, String> method) {
    this.constructor = constructor;
    this.method = method;
  }

}

class ArrayNode<T> extends Node<T> {

  private BiConsumer<T, String> method;

  ArrayNode(ArrayMaker<T> arrayMaker) {
    this(arrayMaker.constructor.get(), arrayMaker.method);
  }

  ArrayNode(T t, BiConsumer<T, String> method) {
    super(t);
    this.method = method;
  }

  @Override
  public Chainable next() {
    return new StringAcceptorNode<>(get(), method);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class ArraySubObjectNode<T> extends Node<T> {

  private final ArraySubObjectMaker<T> maker;

  ArraySubObjectNode(ArraySubObjectMaker<T> maker) {
    super(maker.constructor.get());
    this.maker = maker;
  }

  @Override
  public Chainable next() {
    ArraySetterBinding<T, ?> binding = maker.arraySetterBinding;
    return binding.bind(get());
  }

}

class ArraySubObjectMaker<T> {

  final Supplier<T> constructor;
  final ArraySetterBinding<T, ?> arraySetterBinding;

  ArraySubObjectMaker(Supplier<T> constructor, ArraySetterBinding<T, ?> arraySetterBinding) {
    this.constructor = constructor;
    this.arraySetterBinding = arraySetterBinding;
  }

}

class ArraySetterBinding<T, U> {

  private final BiConsumer<T, U> setter;
  private final ArrayMaker<U> arrayMaker;

  ArraySetterBinding(BiConsumer<T, U> setter, ArrayMaker<U> arrayMaker) {
    this.setter = setter;
    this.arrayMaker = arrayMaker;
  }

  ArrayNode<U> bind(T t) {
    U u = arrayMaker.constructor.get();
    setter.accept(t, u);
    return new ArrayNode<>(u, arrayMaker.method);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class DictionaryMaker<T> {

  final Supplier<T> constructor;
  final TriConsumer<T, String, String> method;

  DictionaryMaker(Supplier<T> constructor, TriConsumer<T, String, String> method) {
    this.constructor = constructor;
    this.method = method;
  }

}

class DictionaryNode<T> extends Node<T> {

  private final TriConsumer<T, String, String> method;

  DictionaryNode(DictionaryMaker<T> dictionaryMaker) {
    this(dictionaryMaker.constructor.get(), dictionaryMaker.method);
  }

  DictionaryNode(T t, TriConsumer<T, String, String> method) {
    super(t);
    this.method = method;
  }

  @Override
  public Chainable next(String key) {
    return new StringDictionaryAcceptorNode<>(get(), key, method);
  }

}

//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

class DictionarySubObjectNode<T> extends Node<T> {

  private final DictionarySetterBinding<T, ?> binding;

  public DictionarySubObjectNode(DictionarySubObjectMaker<T> m) {
    super(m.constructor.get());
    this.binding = m.dictionarySetterBinding;
  }

  @Override
  public Chainable next(String key) {
    return binding.bind(get());
  }

}

class DictionarySubObjectMaker<T> {

  final Supplier<T> constructor;
  final DictionarySetterBinding<T, ?> dictionarySetterBinding;

  DictionarySubObjectMaker(
    Supplier<T> constructor,
    DictionarySetterBinding<T, ?> dictionarySetterBinding
  ) {
    this.constructor = constructor;
    this.dictionarySetterBinding = dictionarySetterBinding;
  }

}

class DictionarySetterBinding<T, U> {

  private final BiConsumer<T, U> setter;
  private final DictionaryMaker<U> dictionaryMaker;

  DictionarySetterBinding(BiConsumer<T, U> setter, DictionaryMaker<U> dictionaryMaker) {
    this.setter = setter;
    this.dictionaryMaker = dictionaryMaker;
  }

  DictionaryNode<U> bind(T t) {
    U u = dictionaryMaker.constructor.get();
    setter.accept(t, u);
    return new DictionaryNode<>(u, dictionaryMaker.method);
  }

}
