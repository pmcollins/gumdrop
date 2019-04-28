package gumdrop.test.json;

import gumdrop.json.JsonWriter;
import gumdrop.test.fake.SimplePerson;

import java.time.format.DateTimeFormatter;

class SimplePersonJsonWriter extends JsonWriter<SimplePerson> {

  SimplePersonJsonWriter() {
    addStringGetter("name", SimplePerson::getName);
    addNumericGetter("age", SimplePerson::getAge);
    addStringGetter("birthday", p -> DateTimeFormatter.ISO_INSTANT.format(p.getBirthday()));
  }

}
