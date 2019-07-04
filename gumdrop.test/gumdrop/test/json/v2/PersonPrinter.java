package gumdrop.test.json.v2;

import gumdrop.json.v2.print.MethodPrinter;
import gumdrop.json.v2.print.PojoPrinter;
import gumdrop.json.v2.print.StringPrinter;
import gumdrop.test.fake.Person;

import java.util.List;

class PersonPrinter extends PojoPrinter<Person> {

  static PersonPrinter build() {
    Builder b = new Builder();
    StringPrinter stringPrinter = new StringPrinter();
    b.addMethodPrinter(new MethodPrinter<>("first", Person::getFirst, stringPrinter));
    b.addMethodPrinter(new MethodPrinter<>("last", Person::getLast, stringPrinter));
    return b.build();
  }

  static class Builder extends PojoPrinter.Builder<Person> {

    @Override
    public PersonPrinter build() {
      return new PersonPrinter(getMethods());
    }

  }

  private PersonPrinter(List<MethodPrinter<Person, ?>> methods) {
    super(methods);
  }

}
