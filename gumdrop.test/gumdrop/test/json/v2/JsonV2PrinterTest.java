package gumdrop.test.json.v2;

import gumdrop.json.v2.print.ArrayPrinter;
import gumdrop.json.v2.print.IntPrinter;
import gumdrop.json.v2.print.MapPrinter;
import gumdrop.json.v2.print.StringPrinter;
import gumdrop.test.fake.Person;
import gumdrop.test.util.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static gumdrop.test.util.Asserts.assertEquals;

public class JsonV2PrinterTest extends Test {

  public static void main(String[] args) throws Exception {
    new JsonV2PrinterTest().run();
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
    ArrayPrinter<String> p = new ArrayPrinter<>(new StringPrinter());
    StringBuilder sb = new StringBuilder();
    p.print(sb, List.of("a", "b"));
    assertEquals("[\"a\",\"b\"]", sb.toString());
  }

  private static void intArrayPrinter() {
    ArrayPrinter<Integer> p = new ArrayPrinter<>(new IntPrinter());
    StringBuilder sb = new StringBuilder();
    p.print(sb, List.of(1, 2));
    assertEquals("[1,2]", sb.toString());
  }

  private static void stringStringMapPrinter() {
    Map<String, String> m = new TreeMap<>();
    m.put("a", "yyy");
    m.put("b", "zzz");
    MapPrinter<String> p = new MapPrinter<>(new StringPrinter());
    StringBuilder sb = new StringBuilder();
    p.print(sb, m);
    assertEquals("{\"a\":\"yyy\",\"b\":\"zzz\"}", sb.toString());
  }

  private static void arrayOfArraysPrinter() {
    ArrayPrinter<List<String>> p = new ArrayPrinter<>(new ArrayPrinter<>(new StringPrinter()));
    StringBuilder sb = new StringBuilder();
    List<String> ary = List.of("aaa", "bbb");
    p.print(sb, List.of(ary, ary));
    assertEquals("[[\"aaa\",\"bbb\"],[\"aaa\",\"bbb\"]]", sb.toString());
  }

  private static void arrayOfMapsPrinter() {
    ArrayPrinter<Map<String, String>> p = new ArrayPrinter<>(
      new MapPrinter<>(
        new StringPrinter()
      )
    );
    StringBuilder sb = new StringBuilder();
    Map<String, String> map = Map.of("a", "xxx");
    p.print(sb, List.of(map, map));
    assertEquals(
      "[{\"a\":\"xxx\"},{\"a\":\"xxx\"}]",
      sb.toString()
    );
  }

  private static void mapOfArraysPrinter() {
    MapPrinter<List<String>> m = new MapPrinter<>(
      new ArrayPrinter<>(
        new StringPrinter()
      )
    );
    StringBuilder sb = new StringBuilder();
    m.print(sb, Map.of("foo", List.of("hello")));
    assertEquals("{\"foo\":[\"hello\"]}", sb.toString());
  }

  private static void personPrinter() {
    PersonPrinter p = PersonPrinter.build();
    StringBuilder sb = new StringBuilder();
    p.print(sb, new Person("bilbo", "baggins"));
    assertEquals("{\"first\":\"bilbo\",\"last\":\"baggins\"}", sb.toString());
  }

}
