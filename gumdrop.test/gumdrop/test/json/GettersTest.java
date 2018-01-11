package gumdrop.test.json;

import gumdrop.json.Getters;
import gumdrop.test.pojo.FullNamePerson;
import gumdrop.test.pojo.Name;
import gumdrop.test.pojo.Person;
import gumdrop.test.util.Test;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static gumdrop.test.util.TestUtil.assertEquals;

class GettersTest extends Test {

  private final Getters<FullNamePerson> complexPersonJsonGetters;
  private final Getters<Person> personJsonGetters;
  private final DateTimeFormatter formatter;

  public static void main(String[] args) {
    new GettersTest().run();
  }

  GettersTest() {
    personJsonGetters = setupPerson();
    complexPersonJsonGetters = setupComplexPerson();
    formatter = DateTimeFormatter.ISO_INSTANT;
  }

  private Getters<Person> setupPerson() {
    Getters<Person> personJsonGetters = new Getters<>();
    personJsonGetters.addStringGetter("name", Person::getName);
    personJsonGetters.addNumericGetter("age", Person::getAge);
    personJsonGetters.addStringGetter("birthday", p -> formatter.format(p.getBirthday()));
    return personJsonGetters;
  }

  private Getters<FullNamePerson> setupComplexPerson() {
    Getters<FullNamePerson> personGetters;
    Getters<Name> nameGetters = new Getters<>();
    nameGetters.addStringGetter("first", Name::getFirst);
    nameGetters.addStringGetter("last", Name::getLast);
    personGetters = new Getters<>();
    personGetters.addNumericGetter("age", FullNamePerson::getAge);
    personGetters.addMember("name", FullNamePerson::getName, nameGetters);
    return personGetters;
  }

  @Override
  public void run() {
    personToJson();
    complexPersonToJson();
    mapToJson();
    dynamicMap();
  }

  private void personToJson() {
    Person person = new Person();
    person.setName("bobo");
    person.setAge(25);
    person.setBirthday(Instant.ofEpochMilli(700_000_000_000L));
    assertEquals(
      "{\"name\":\"bobo\",\"age\":25,\"birthday\":\"1992-03-07T20:26:40Z\"}",
      personJsonGetters.getJson(person)
    );
  }

  private void complexPersonToJson() {
    FullNamePerson complexPerson = new FullNamePerson();
    complexPerson.setAge(42);
    Name name = new Name();
    name.setFirst("lile");
    name.setLast("collinson");
    complexPerson.setName(name);
    String json = complexPersonJsonGetters.getJson(complexPerson);
    assertEquals("{\"age\":42,\"name\":{\"first\":\"lile\",\"last\":\"collinson\"}}", json);
  }

  private void mapToJson() {
    Map<String, Name> map = new HashMap<>();
    map.put("foo", new Name("foo", "bar"));
    map.put("baz", new Name("baz", "glarch"));
    Getters<Name> nameGetters = new Getters<>();
    nameGetters.addStringGetter("first", Name::getFirst);
    nameGetters.addStringGetter("last", Name::getLast);
    Getters<Map<String, Name>> mapGetters = new Getters<>();
    mapGetters.addMember("foo", m -> m.get("foo"), nameGetters);
    mapGetters.addMember("bar", m -> m.get("baz"), nameGetters);
    String json = mapGetters.getJson(map);
    assertEquals("{\"foo\":{\"first\":\"foo\",\"last\":\"bar\"},\"bar\":{\"first\":\"baz\",\"last\":\"glarch\"}}", json);
  }

  private void dynamicMap() {
    Map<String, Name> map = new HashMap<>();
    map.put("foo", new Name("foo", "bar"));
    map.put("baz", new Name("baz", "glarch"));
    map.put("quux", new Name("xxx", "yyy"));
    Getters<Name> nameGetters = new Getters<>();
    nameGetters.addStringGetter("first", Name::getFirst);
    nameGetters.addStringGetter("last", Name::getLast);
    Getters<Map<String, Name>> mapGetters = new Getters<>();
    mapGetters.setKeyFunction(Map::keySet);
    mapGetters.setMemberFunction(Map::get, nameGetters);
    String json = mapGetters.getJson(map);
    System.out.println("json = [" + json + "]");
  }

}
