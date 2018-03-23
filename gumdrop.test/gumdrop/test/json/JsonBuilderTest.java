package gumdrop.test.json;

import gumdrop.json.JsonConverter;
import gumdrop.test.pojo.FullNamePerson;
import gumdrop.test.pojo.Name;
import gumdrop.test.pojo.Person;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import java.time.Instant;

import static gumdrop.test.util.Asserts.assertEquals;

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
    JsonConverter<Person> personBuilder = new PersonConverter();
    Person p = new Person();
    p.setName("lile");
    p.setAge(10);
    String json = personBuilder.toJson(p);
    Asserts.assertEquals("{\"name\":\"lile\",\"age\":10}", json);
    Person fromJson = personBuilder.fromJson(json);
    Asserts.assertEquals(p, fromJson);
  }

  private void fullNamePerson() {
    JsonConverter<FullNamePerson> personConverter = new JsonConverter<>(FullNamePerson::new);
    personConverter.addIntField("age", FullNamePerson::getAge, FullNamePerson::setAge);
    JsonConverter<Name> nameConverter = new JsonConverter<>(Name::new);
    nameConverter.addStringField("first", Name::getFirst, Name::setFirst);
    nameConverter.addStringField("last", Name::getLast, Name::setLast);
    personConverter.addSubFields("name", FullNamePerson::getName, FullNamePerson::setName, nameConverter);
    FullNamePerson p = new FullNamePerson();
    p.setAge(10);
    Name name = new Name();
    name.setFirst("lile");
    name.setLast("collinson");
    p.setName(name);
    String json = personConverter.toJson(p);
    FullNamePerson rebuilt = personConverter.fromJson(json);
    Asserts.assertEquals(p, rebuilt);
  }

  private void integration() {
    JsonConverter<Person> personConverter = new JsonConverter<>(Person::new);
    personConverter.addStringField("name", Person::getName, Person::setName);
    personConverter.addIntField("age", Person::getAge, Person::setAge);
    personConverter.addField(
      "birthday",
      Person::getBirthday,
      Person::setBirthday,
      new InstantConverter()
    );

    Person person = new Person();
    person.setName("Frodo");
    person.setAge(25);
    person.setBirthday(Instant.ofEpochMilli(700_000_000_000L));

    String json = personConverter.toJson(person);
    assertEquals(
      "{\"name\":\"Frodo\",\"age\":25,\"birthday\":\"1992-03-07T20:26:40Z\"}",
      json
    );
    Person fromJson = personConverter.fromJson(json);
    assertEquals(person, fromJson);
  }

  private static class PersonConverter extends JsonConverter<Person> {

    PersonConverter() {
      super(Person::new);
      addStringField("name", Person::getName, Person::setName);
      addIntField("age", Person::getAge, Person::setAge);
    }

  }

}
