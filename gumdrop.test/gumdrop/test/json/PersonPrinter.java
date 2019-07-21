package gumdrop.test.json;

import gumdrop.json.print.MethodPrinter;
import gumdrop.json.print.ObjectPrinter;
import gumdrop.json.print.StringPrinter;
import gumdrop.test.fake.Person;

import java.util.List;

class PersonPrinter extends ObjectPrinter<Person> {

  static PersonPrinter build() {
    Builder b = new Builder();
    StringPrinter stringPrinter = new StringPrinter();
    b.addPrinter(new MethodPrinter<>("first", Person::getFirst, stringPrinter));
    b.addPrinter(new MethodPrinter<>("last", Person::getLast, stringPrinter));
    return b.build();
  }

  static class Builder extends ObjectPrinter.Builder<Person> {

    @Override
    public PersonPrinter build() {
      return new PersonPrinter(getMethods());
    }

  }

  private PersonPrinter(List<MethodPrinter<Person, ?>> methods) {
    super(methods);
  }

}
