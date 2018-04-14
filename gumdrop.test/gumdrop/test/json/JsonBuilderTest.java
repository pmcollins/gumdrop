package gumdrop.test.json;

import gumdrop.json.JsonBuilder;
import gumdrop.test.util.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonBuilderTest extends Test {

  public static void main(String[] args) {
    new JsonBuilderTest().run();
  }

  @Override
  public void run() {
    JsonBuilder<HashMap<String, String>> mapBuilder = new JsonBuilder<>(HashMap::new);
    mapBuilder.addSetter("*", Map::put);

    JsonBuilder<Thing> thingBuilder = new JsonBuilder<>(Thing::new);
    thingBuilder.addSetter("name", Thing::setName);
    thingBuilder.addMember("map", Thing::setMap, mapBuilder);

    Thing rebuilt = thingBuilder.fromJson("{\"name\" : \"foo\", \"map\" : {\"aaa\" : \"bbb\"}}");
    System.out.println("rebuilt = [" + rebuilt + "]");
  }

  class Thing {

    private String name;
    private Map<String, String> map;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Map<String, String> getMap() {
      return map;
    }

    void setMap(Map<String, String> map) {
      this.map = map;
    }

  }

}
