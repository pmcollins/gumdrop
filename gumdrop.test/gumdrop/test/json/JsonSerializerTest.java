package gumdrop.test.json;

import gumdrop.json.print.ArraySerializer;
import gumdrop.json.print.IntSerializer;
import gumdrop.json.print.MapSerializer;
import gumdrop.json.print.StringSerializer;
import gumdrop.test.fake.SimplePerson;
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
    stringArrayPrinter();
    intArrayPrinter();
    stringStringMapPrinter();
    arrayOfArraysPrinter();
    arrayOfMapsPrinter();
    mapOfArraysPrinter();
    personPrinter();
  }

  private static void stringArrayPrinter() {
    ArraySerializer<String> p = new ArraySerializer<>(new StringSerializer());
    String json = p.toJson(List.of("a", "b"));
    assertEquals("[\"a\",\"b\"]", json);
  }

  private static void intArrayPrinter() {
    ArraySerializer<Integer> p = new ArraySerializer<>(new IntSerializer());
    String json = p.toJson(List.of(1, 2));
    assertEquals("[1,2]", json);
  }

  private static void stringStringMapPrinter() {
    Map<String, String> m = new TreeMap<>();
    m.put("a", "yyy");
    m.put("b", "zzz");
    MapSerializer<String> p = new MapSerializer<>(new StringSerializer());
    String json = p.toJson(m);
    assertEquals("{\"a\":\"yyy\",\"b\":\"zzz\"}", json);
  }

  private static void arrayOfArraysPrinter() {
    ArraySerializer<List<String>> p = new ArraySerializer<>(new ArraySerializer<>(new StringSerializer()));
    List<String> ary = List.of("aaa", "bbb");
    String json = p.toJson(List.of(ary, ary));
    assertEquals("[[\"aaa\",\"bbb\"],[\"aaa\",\"bbb\"]]", json);
  }

  private static void arrayOfMapsPrinter() {
    ArraySerializer<Map<String, String>> p = new ArraySerializer<>(
      new MapSerializer<>(
        new StringSerializer()
      )
    );
    Map<String, String> map = Map.of("a", "xxx");
    String json = p.toJson(List.of(map, map));
    assertEquals(
      "[{\"a\":\"xxx\"},{\"a\":\"xxx\"}]",
      json
    );
  }

  private static void mapOfArraysPrinter() {
    MapSerializer<List<String>> m = new MapSerializer<>(
      new ArraySerializer<>(
        new StringSerializer()
      )
    );
    String json = m.toJson(Map.of("foo", List.of("hello")));
    assertEquals("{\"foo\":[\"hello\"]}", json);
  }

  private static void personPrinter() {
    PersonSerializer p = PersonSerializer.build();
    String json = p.toJson(new SimplePerson("bilbo", "baggins"));
    assertEquals("{\"first\":\"bilbo\",\"last\":\"baggins\"}", json);
  }

}
