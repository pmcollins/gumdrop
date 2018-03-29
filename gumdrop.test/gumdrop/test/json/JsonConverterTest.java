package gumdrop.test.json;

import gumdrop.json.JsonConverter;
import gumdrop.test.pojo.Person;
import gumdrop.test.pojo.Name;
import gumdrop.test.pojo.SimplePerson;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;

import java.time.Instant;

import static gumdrop.test.util.Asserts.assertEquals;

class JsonConverterTest extends Test {

  public static void main(String[] args) {
    new JsonConverterTest().run();
  }

  @Override
  public void run() {
    person();
    fullNamePerson();
    integration();
  }

  private void person() {
    JsonConverter<SimplePerson> personBuilder = new PersonConverter();
    SimplePerson p = new SimplePerson();
    p.setName("lile");
    p.setAge(10);
    String json = personBuilder.toString(p);
    Asserts.assertEquals("{\"name\":\"lile\",\"age\":10}", json);
    SimplePerson fromJson = personBuilder.fromString(json);
    Asserts.assertEquals(p, fromJson);
  }

  private void fullNamePerson() {
    JsonConverter<Person> personConverter = new JsonConverter<>(Person::new);
    personConverter.addIntField("age", Person::getAge, Person::setAge);
    JsonConverter<Name> nameConverter = new JsonConverter<>(Name::new);
    nameConverter.addStringField("first", Name::getFirst, Name::setFirst);
    nameConverter.addStringField("last", Name::getLast, Name::setLast);
    personConverter.addSubConverter("name", Person::getName, Person::setName, nameConverter);
    Person p = new Person();
    p.setAge(111);
    Name name = new Name();
    name.setFirst("Bilbo");
    name.setLast("Baggins");
    p.setName(name);
    String json = personConverter.toString(p);
    System.out.println(json);
    Person rebuilt = personConverter.fromString(json);
    Asserts.assertEquals(p, rebuilt);
  }

  private void integration() {
    JsonConverter<SimplePerson> personConverter = new JsonConverter<>(SimplePerson::new);
    personConverter.addStringField("name", SimplePerson::getName, SimplePerson::setName);
    personConverter.addIntField("age", SimplePerson::getAge, SimplePerson::setAge);
    personConverter.addField(
      "birthday",
      SimplePerson::getBirthday,
      SimplePerson::setBirthday,
      new InstantConverter()
    );

    SimplePerson person = new SimplePerson();
    person.setName("Frodo");
    person.setAge(25);
    person.setBirthday(Instant.parse("1900-01-01T01:00:00Z"));

    String json = personConverter.toString(person);
    assertEquals(
      "{\"name\":\"Frodo\",\"age\":25,\"birthday\":\"1900-01-01T01:00:00Z\"}",
      json
    );
    SimplePerson fromJson = personConverter.fromString(json);
    assertEquals(person, fromJson);
  }

  private static class PersonConverter extends JsonConverter<SimplePerson> {

    PersonConverter() {
      super(SimplePerson::new);
      addStringField("name", SimplePerson::getName, SimplePerson::setName);
      addIntField("age", SimplePerson::getAge, SimplePerson::setAge);
    }

  }

}
