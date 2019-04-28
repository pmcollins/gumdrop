package gumdrop.test.json;

import gumdrop.json.JsonConverter;
import gumdrop.test.fake.InstantConverter;
import gumdrop.test.fake.NamedPerson;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.BirthdayPerson;
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
    JsonConverter<BirthdayPerson> personBuilder = new PersonConverter();
    BirthdayPerson p = new BirthdayPerson();
    p.setName("lile");
    p.setAge(10);
    String json = personBuilder.toString(p);
    Asserts.assertEquals("{\"name\":\"lile\",\"age\":10}", json);
    BirthdayPerson fromJson = personBuilder.fromString(json);
    Asserts.assertEquals(p, fromJson);
  }

  private void fullNamePerson() {
    JsonConverter<NamedPerson> personConverter = new JsonConverter<>(NamedPerson::new);
    personConverter.addIntField("age", NamedPerson::getAge, NamedPerson::setAge);
    JsonConverter<Name> nameConverter = new JsonConverter<>(Name::new);
    nameConverter.addField("first", Name::getFirst, Name::setFirst);
    nameConverter.addField("last", Name::getLast, Name::setLast);
    personConverter.addSubConverter("name", NamedPerson::getName, NamedPerson::setName, nameConverter);
    NamedPerson p = new NamedPerson();
    p.setAge(111);
    Name name = new Name();
    name.setFirst("Bilbo");
    name.setLast("Baggins");
    p.setName(name);
    String json = personConverter.toString(p);
    NamedPerson rebuilt = personConverter.fromString(json);
    Asserts.assertEquals(p, rebuilt);
  }

  private void integration() {
    JsonConverter<BirthdayPerson> personConverter = new JsonConverter<>(BirthdayPerson::new);
    personConverter.addField("name", BirthdayPerson::getName, BirthdayPerson::setName);
    personConverter.addIntField("age", BirthdayPerson::getAge, BirthdayPerson::setAge);
    personConverter.addField(
      "birthday",
      BirthdayPerson::getBirthday,
      BirthdayPerson::setBirthday,
      new InstantConverter()
    );

    BirthdayPerson person = new BirthdayPerson();
    person.setName("Frodo");
    person.setAge(25);
    person.setBirthday(Instant.parse("1900-01-01T01:00:00Z"));

    String json = personConverter.toString(person);
    assertEquals(
      "{\"name\":\"Frodo\",\"age\":25,\"birthday\":\"1900-01-01T01:00:00Z\"}",
      json
    );
    BirthdayPerson fromJson = personConverter.fromString(json);
    assertEquals(person, fromJson);
  }

  private static class PersonConverter extends JsonConverter<BirthdayPerson> {

    PersonConverter() {
      super(BirthdayPerson::new);
      addField("name", BirthdayPerson::getName, BirthdayPerson::setName);
      addIntField("age", BirthdayPerson::getAge, BirthdayPerson::setAge);
    }

  }

}
