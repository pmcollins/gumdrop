package gumdrop.json.test;

import gumdrop.json.Getters;
import gumdrop.test.Test;
import gumdrop.test.TestUtil;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

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
  }

  private void personToJson() {
    Person person = new Person();
    person.setName("bobo");
    person.setAge(25);
    person.setBirthday(Instant.ofEpochMilli(700_000_000_000L));
    TestUtil.assertEquals(
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
    TestUtil.assertEquals("{\"age\":42,\"name\":{\"first\":\"lile\",\"last\":\"collinson\"}}", json);
  }

}
