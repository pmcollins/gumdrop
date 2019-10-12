package gumdrop.test.json;

import gumdrop.json.FieldBinding;
import gumdrop.json.PojoDeserializer;
import gumdrop.json.StringDeserializer;
import gumdrop.test.fake.Name;

import java.util.List;
import java.util.function.Consumer;

class NamePojoDeserializer extends PojoDeserializer<Name> {

  NamePojoDeserializer() {
    this(null);
  }

  NamePojoDeserializer(Consumer<Name> listener) {
    super(Name::new, List.of(
      new FieldBinding<>("first", Name::setFirst, StringDeserializer::new),
      new FieldBinding<>("last", Name::setLast, StringDeserializer::new)
    ), listener);
  }

}
