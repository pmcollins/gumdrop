package gumdrop.test.json;

import gumdrop.json.print.IntSerializer;
import gumdrop.json.print.ObjectSerializer;
import gumdrop.json.print.StringSerializer;
import gumdrop.test.fake.Name;
import gumdrop.test.fake.Person;

class NameSerializer extends ObjectSerializer<Name> {

  NameSerializer() {
    StringSerializer stringSerializer = new StringSerializer();
    addMethodSerializer("first", Name::getFirst, stringSerializer);
    addMethodSerializer("last", Name::getLast, stringSerializer);
  }

}

