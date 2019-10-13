package gumdrop.test.json;

import gumdrop.json.Chainable;
import gumdrop.json.NullableDeserializer;
import gumdrop.test.fake.Name;

import java.util.function.Consumer;

class NameNullableDeserializer extends NullableDeserializer<Name> {

  NameNullableDeserializer(Consumer<Name> listener) {
    super(listener);
  }

  @Override
  public Chainable next() {
    Name name = new Name();
    setValue(name);
    return new NameAttributesDeserializer(name);
  }

}
