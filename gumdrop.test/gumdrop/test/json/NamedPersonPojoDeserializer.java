package gumdrop.test.json;

import gumdrop.json.FieldBinding;
import gumdrop.json.IntDeserializer;
import gumdrop.json.PojoDeserializer;
import gumdrop.test.fake.NamedPerson;

import java.util.List;

class NamedPersonPojoDeserializer extends PojoDeserializer<NamedPerson> {

  NamedPersonPojoDeserializer() {
    super(NamedPerson::new, List.of(
      new FieldBinding<>("age", NamedPerson::setAge, IntDeserializer::new),
      new FieldBinding<>("name", NamedPerson::setName, NamePojoDeserializer::new)
    ));
  }

}
