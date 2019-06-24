package gumdrop.test.json.v2;

import gumdrop.json.v2.PojoCreatorNode;

class PersonCreatorNode extends PojoCreatorNode<Person> {

  PersonCreatorNode() {
    super(new Person(), key -> {
      if ("first".equals(key)) {
        return Person::setFirst;
      } else if ("last".equals(key)) {
        return Person::setLast;
      }
      return null;
    });
  }

}
