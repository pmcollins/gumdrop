package gumdrop.test.json;

import gumdrop.json.JsonBuilder;
import gumdrop.test.pojo.FullNamePerson;
import gumdrop.test.pojo.Name;
import gumdrop.test.pojo.Person;
import gumdrop.test.util.Test;
import gumdrop.test.util.TestUtil;

import java.time.Instant;

import static gumdrop.test.util.TestUtil.assertEquals;

class JsonBuilderTest extends Test {

  public static void main(String[] args) {
    new JsonBuilderTest().run();
  }

  @Override
  public void run() {
    person();
    fullNamePerson();
    integration();
  }

  private void person() {
    JsonBuilder<Person> personBuilder = new PersonBuilder();
    Person p = new Person();
    p.setName("lile");
    p.setAge(10);
    String json = personBuilder.toJson(p);
    TestUtil.assertEquals("{\"name\":\"lile\",\"age\":10}", json);
    Person fromJson = personBuilder.fromJson(json);
    TestUtil.assertEquals(p, fromJson);
  }

  private void fullNamePerson() {
    JsonBuilder<FullNamePerson> personBuilder = new JsonBuilder<>(FullNamePerson::new);
    personBuilder.addIntField("age", FullNamePerson::getAge, FullNamePerson::setAge);
    JsonBuilder<Name> nameBuilder = new JsonBuilder<>(Name::new);
    nameBuilder.addStringField("first", Name::getFirst, Name::setFirst);
    nameBuilder.addStringField("last", Name::getLast, Name::setLast);
    personBuilder.addSubFields("name", FullNamePerson::getName, FullNamePerson::setName, nameBuilder);
    FullNamePerson p = new FullNamePerson();
    p.setAge(10);
    Name name = new Name();
    name.setFirst("lile");
    name.setLast("collinson");
    p.setName(name);
    String json = personBuilder.toJson(p);
    FullNamePerson rebuilt = personBuilder.fromJson(json);
    TestUtil.assertEquals(p, rebuilt);
  }

  private void integration() {
    JsonBuilder<Person> personBuilder = new JsonBuilder<>(Person::new);
    personBuilder.addStringField("name", Person::getName, Person::setName);
    personBuilder.addIntField("age", Person::getAge, Person::setAge);
    personBuilder.addField(
      "birthday",
      Person::getBirthday,
      Person::setBirthday,
      new InstantConverter()
    );

    Person person = new Person();
    person.setName("Frodo");
    person.setAge(25);
    person.setBirthday(Instant.ofEpochMilli(700_000_000_000L));

    String json = personBuilder.toJson(person);
    assertEquals(
      "{\"name\":\"Frodo\",\"age\":25,\"birthday\":\"1992-03-07T20:26:40Z\"}",
      json
    );
    Person fromJson = personBuilder.fromJson(json);
    assertEquals(person, fromJson);
  }

  private static class PersonBuilder extends JsonBuilder<Person> {

    PersonBuilder() {
      super(Person::new);
      addStringField("name", Person::getName, Person::setName);
      addIntField("age", Person::getAge, Person::setAge);
    }

  }

}
