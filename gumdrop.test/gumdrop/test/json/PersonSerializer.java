package gumdrop.test.json;

import gumdrop.json.print.MethodSerializer;
import gumdrop.json.print.ObjectSerializer;
import gumdrop.json.print.StringSerializer;
import gumdrop.test.fake.SimplePerson;

import java.util.List;

class PersonSerializer extends ObjectSerializer<SimplePerson> {

  static PersonSerializer build() {
    Builder b = new Builder();
    StringSerializer stringPrinter = new StringSerializer();
    b.addSerializer(new MethodSerializer<>("first", SimplePerson::getFirst, stringPrinter));
    b.addSerializer(new MethodSerializer<>("last", SimplePerson::getLast, stringPrinter));
    return b.build();
  }

  static class Builder extends ObjectSerializer.Builder<SimplePerson> {

    @Override
    public PersonSerializer build() {
      return new PersonSerializer(getMethods());
    }

  }

  private PersonSerializer(List<MethodSerializer<SimplePerson, ?>> methods) {
    super(methods);
  }

}
