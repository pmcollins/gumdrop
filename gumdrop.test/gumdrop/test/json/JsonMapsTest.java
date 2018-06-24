package gumdrop.test.json;

import gumdrop.json.JsonBuilder;
import gumdrop.json.JsonConverter;
import gumdrop.json.JsonMapConverter;
import gumdrop.json.JsonWriter;
import gumdrop.test.util.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static gumdrop.test.util.Asserts.assertEquals;

public class JsonMapsTest extends Test {

  private static class Thing {

    private String name;
    private Map<String, String> map;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    Map<String, String> getMap() {
      return map;
    }

    void setMap(Map<String, String> map) {
      this.map = map;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Thing thing = (Thing) o;
      return Objects.equals(name, thing.name) && Objects.equals(map, thing.map);
    }

  }

  public static void main(String[] args) {
    new JsonMapsTest().run();
  }

  @Override
  public void run() {
    builder();
    writer();
    converter();
    nuthing();
  }

  private void builder() {
    JsonBuilder<HashMap<String, String>> mapBuilder = new JsonBuilder<>(HashMap::new);
    mapBuilder.addCatchallSetter(Map::put);

    JsonBuilder<Thing> thingBuilder = new JsonBuilder<>(Thing::new);
    thingBuilder.addSetter("name", Thing::setName);
    thingBuilder.addMember("map", Thing::setMap, mapBuilder);

    Thing thing = thingBuilder.fromJson("{\"name\" : \"foo\", \"map\" : {\"aaa\" : \"bbb\"}}");
    assertEquals("foo", thing.getName());
    Map<String, String> map = thing.getMap();
    assertEquals("bbb", map.get("aaa"));
  }

  private void writer() {
    JsonWriter<Map<String, String>> mapWriter = new JsonWriter<>();
    mapWriter.setMapFunctions(Map::keySet, Map::get);
    String json = mapWriter.apply(mkMap());
    assertEquals("{\"a\":\"b\",\"c\":\"d\"}", json);

    JsonWriter<Thing> thingWriter = new JsonWriter<>();
    thingWriter.addStringGetter("name", Thing::getName);
    thingWriter.addMember("map", Thing::getMap, mapWriter);

    Thing thing = new Thing();
    thing.setName("bilbo");
    thing.setMap(mkMap());
    String thingJson = thingWriter.apply(thing);
    assertEquals("{\"name\":\"bilbo\",\"map\":{\"a\":\"b\",\"c\":\"d\"}}", thingJson);
  }

  private void converter() {
    JsonMapConverter mapConverter = new JsonMapConverter();
    Map<String, String> map = mkMap();
    assertEquals(map, mapConverter.fromString(mapConverter.toString(map)));
    JsonConverter<Thing> c = new JsonConverter<>(Thing::new);
    c.addField("name", Thing::getName, Thing::setName);
    c.addSubConverter("map", Thing::getMap, Thing::setMap, mapConverter);
    Thing thing = new Thing();
    thing.setName("bilbo");
    thing.setMap(map);
    assertEquals(thing, c.fromString(c.toString(thing)));
  }

  private static Map<String, String> mkMap() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("a", "b");
    map.put("c", "d");
    return map;
  }

  private void nuthing() {
    JsonMapConverter c = new JsonMapConverter();
    Map<String, String> map = c.fromString("{}");
    System.out.println("map = " + map);
  }

}
