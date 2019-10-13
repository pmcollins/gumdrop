package gumdrop.test.json;

import gumdrop.json.print.ArraySerializer;
import gumdrop.json.print.IntSerializer;
import gumdrop.json.print.MapSerializer;
import gumdrop.json.print.StringSerializer;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.Person;
import gumdrop.test.util.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static gumdrop.test.util.Asserts.assertEquals;

public class JsonSerializerTest extends Test {

  public static void main(String[] args) throws Exception {
    new JsonSerializerTest().run();
  }

  @Override
  public void run() throws Exception {
    stringArraySerializer();
    intArraySerializer();
    stringStringMapSerializer();
    arrayOfArraysSerializer();
    arrayOfMapsSerializer();
    mapOfArraysSerializer();
    nameSerializer();
    personSerializer();
  }

  private static void stringArraySerializer() {
    ArraySerializer<String> s = new ArraySerializer<>(new StringSerializer());
    String json = s.toJson(List.of("a", "b"));
    assertEquals("[\"a\",\"b\"]", json);
  }

  private static void intArraySerializer() {
    ArraySerializer<Integer> s = new ArraySerializer<>(new IntSerializer());
    String json = s.toJson(List.of(1, 2));
    assertEquals("[1,2]", json);
  }

  private static void stringStringMapSerializer() {
    Map<String, String> m = new TreeMap<>();
    m.put("a", "yyy");
    m.put("b", "zzz");
    MapSerializer<String> s = new MapSerializer<>(new StringSerializer());
    String json = s.toJson(m);
    assertEquals("{\"a\":\"yyy\",\"b\":\"zzz\"}", json);
  }

  private static void arrayOfArraysSerializer() {
    ArraySerializer<List<String>> s = new ArraySerializer<>(new ArraySerializer<>(new StringSerializer()));
    List<String> ary = List.of("aaa", "bbb");
    String json = s.toJson(List.of(ary, ary));
    assertEquals("[[\"aaa\",\"bbb\"],[\"aaa\",\"bbb\"]]", json);
  }

  private static void arrayOfMapsSerializer() {
    ArraySerializer<Map<String, String>> s = new ArraySerializer<>(
      new MapSerializer<>(
        new StringSerializer()
      )
    );
    Map<String, String> map = Map.of("a", "xxx");
    String json = s.toJson(List.of(map, map));
    assertEquals(
      "[{\"a\":\"xxx\"},{\"a\":\"xxx\"}]",
      json
    );
  }

  private static void mapOfArraysSerializer() {
    MapSerializer<List<String>> s = new MapSerializer<>(
      new ArraySerializer<>(
        new StringSerializer()
      )
    );
    String json = s.toJson(Map.of("foo", List.of("hello")));
    assertEquals("{\"foo\":[\"hello\"]}", json);
  }

  private static void nameSerializer() {
    NameSerializer s = new NameSerializer();
    String json = s.toJson(new Name("bilbo", "baggins"));
    assertEquals("{\"first\":\"bilbo\",\"last\":\"baggins\"}", json);
  }

  private static void personSerializer() {
    Name name = new Name("bilbo", "baggins");
    Person person = new Person();
    person.setName(name);
    person.setAge(111);
    PersonSerializer s = new PersonSerializer();
    String json = s.toJson(person);
    assertEquals("{\"age\":111,\"name\":{\"first\":\"bilbo\",\"last\":\"baggins\"}}", json);
  }

}
