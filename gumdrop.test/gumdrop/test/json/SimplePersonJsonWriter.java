package gumdrop.test.json;

import gumdrop.json.JsonWriter;
import gumdrop.test.fake.BirthdayPerson;

import java.time.format.DateTimeFormatter;

class SimplePersonJsonWriter extends JsonWriter<BirthdayPerson> {

  SimplePersonJsonWriter() {
    addStringGetter("name", BirthdayPerson::getName);
    addNumericGetter("age", BirthdayPerson::getAge);
    addStringGetter("birthday", p -> DateTimeFormatter.ISO_INSTANT.format(p.getBirthday()));
  }

}
