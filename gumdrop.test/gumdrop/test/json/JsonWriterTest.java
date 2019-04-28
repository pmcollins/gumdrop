package gumdrop.test.json;

import gumdrop.json.JsonWriter;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.Person;
import gumdrop.test.fake.SimplePerson;
import gumdrop.test.util.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static gumdrop.test.util.Asserts.assertEquals;

class JsonWriterTest extends Test {

  private final JsonWriter<Person> complexPersonJsonJsonWriter;
  private final JsonWriter<SimplePerson> simplePersonJsonWriter;

  public static void main(String[] args) {
    new JsonWriterTest().run();
  }

  JsonWriterTest() {
    simplePersonJsonWriter = new SimplePersonJsonWriter();
    complexPersonJsonJsonWriter = setupComplexPerson();
  }

  private static JsonWriter<Person> setupComplexPerson() {
    JsonWriter<Person> personJsonWriter;
    JsonWriter<Name> nameJsonWriter = new JsonWriter<>();
    nameJsonWriter.addStringGetter("first", Name::getFirst);
    nameJsonWriter.addStringGetter("last", Name::getLast);
    personJsonWriter = new JsonWriter<>();
    personJsonWriter.addNumericGetter("age", Person::getAge);
    personJsonWriter.addSubWriter("name", Person::getName, nameJsonWriter);
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
    SimplePerson person = new SimplePerson();
    person.setName("bobo");
    person.setAge(25);
    person.setBirthday(Instant.ofEpochMilli(700_000_000_000L));
    assertEquals(
      "{\"name\":\"bobo\",\"age\":25,\"birthday\":\"1992-03-07T20:26:40Z\"}",
      simplePersonJsonWriter.apply(person)
    );
  }

  private void complexPersonToJson() {
    Person complexPerson = new Person();
    complexPerson.setAge(42);
    Name name = new Name();
    name.setFirst("lile");
    name.setLast("collinson");
    complexPerson.setName(name);
    String json = complexPersonJsonJsonWriter.apply(complexPerson);
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
