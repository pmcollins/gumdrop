package gumdrop.test.json.v1;

import gumdrop.json.v1.JsonWriter;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.NamedPerson;
import gumdrop.test.fake.BirthdayPerson;
import gumdrop.test.util.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static gumdrop.test.util.Asserts.assertEquals;

class JsonWriterTest extends Test {

  private final JsonWriter<NamedPerson> complexPersonJsonWriter;
  private final JsonWriter<BirthdayPerson> simplePersonJsonWriter;

  public static void main(String[] args) {
    new JsonWriterTest().run();
  }

  JsonWriterTest() {
    simplePersonJsonWriter = new SimplePersonJsonWriter();
    complexPersonJsonWriter = setupComplexPerson();
  }

  private static JsonWriter<NamedPerson> setupComplexPerson() {
    JsonWriter<NamedPerson> personJsonWriter;
    JsonWriter<Name> nameJsonWriter = new JsonWriter<>();
    nameJsonWriter.addStringGetter("first", Name::getFirst);
    nameJsonWriter.addStringGetter("last", Name::getLast);
    personJsonWriter = new JsonWriter<>();
    personJsonWriter.addNumericGetter("age", NamedPerson::getAge);
    personJsonWriter.addSubWriter("name", NamedPerson::getName, nameJsonWriter);
    return personJsonWriter;
  }

  @Override
  public void run() {
    personToJson();
    complexPersonToJson();
    mapToJson();
    dynamicMap();
  }

  private void personToJson() {
    BirthdayPerson person = new BirthdayPerson();
    person.setName("bobo");
    person.setAge(25);
    person.setBirthday(Instant.ofEpochMilli(700_000_000_000L));
    assertEquals(
      "{\"name\":\"bobo\",\"age\":25,\"birthday\":\"1992-03-07T20:26:40Z\"}",
      simplePersonJsonWriter.apply(person)
    );
  }

  private void complexPersonToJson() {
    NamedPerson complexNamedPerson = new NamedPerson();
    complexNamedPerson.setAge(42);
    Name name = new Name();
    name.setFirst("lile");
    name.setLast("collinson");
    complexNamedPerson.setName(name);
    String json = complexPersonJsonWriter.apply(complexNamedPerson);
    assertEquals("{\"age\":42,\"name\":{\"first\":\"lile\",\"last\":\"collinson\"}}", json);
  }

  private static void mapToJson() {
    Map<String, Name> map = new HashMap<>();
    map.put("foo", new Name("foo", "bar"));
    map.put("baz", new Name("baz", "glarch"));
    JsonWriter<Name> nameJsonWriter = new JsonWriter<>();
    nameJsonWriter.addStringGetter("first", Name::getFirst);
    nameJsonWriter.addStringGetter("last", Name::getLast);
    JsonWriter<Map<String, Name>> mapJsonWriter = new JsonWriter<>();
    mapJsonWriter.addSubWriter("foo", m -> m.get("foo"), nameJsonWriter);
    mapJsonWriter.addSubWriter("bar", m -> m.get("baz"), nameJsonWriter);
    String json = mapJsonWriter.apply(map);
    assertEquals("{\"foo\":{\"first\":\"foo\",\"last\":\"bar\"},\"bar\":{\"first\":\"baz\",\"last\":\"glarch\"}}", json);
  }

  private static void dynamicMap() {
    Map<String, Name> map = new HashMap<>();
    map.put("foo", new Name("foo", "bar"));
    map.put("baz", new Name("baz", "glarch"));
    map.put("quux", new Name("frob", "snarf"));

    JsonWriter<Name> nameJsonWriter = new JsonWriter<>();
    nameJsonWriter.addStringGetter("first", Name::getFirst);
    nameJsonWriter.addStringGetter("last", Name::getLast);

    JsonWriter<Map<String, Name>> mapJsonWriter = new JsonWriter<>();
    mapJsonWriter.setKeyFunction(Map::keySet);
    mapJsonWriter.setCatchallBinding(Map::get, nameJsonWriter);
    String json = mapJsonWriter.apply(map);
    assertEquals(
      "{\"quux\":{\"first\":\"frob\",\"last\":\"snarf\"}," +
      "\"foo\":{\"first\":\"foo\",\"last\":\"bar\"}," +
      "\"baz\":{\"first\":\"baz\",\"last\":\"glarch\"}}",
      json
    );
  }

}
