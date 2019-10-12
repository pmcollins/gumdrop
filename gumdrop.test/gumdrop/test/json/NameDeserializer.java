package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;
import gumdrop.test.fake.Name;

import java.util.function.Consumer;

class NameDeserializer extends NullableDeserializer<Name> {

  NameDeserializer(Consumer<Name> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    Name name = new Name();
    setValue(name);
    return new NameAttributesDeserializer(name);
  }

}
