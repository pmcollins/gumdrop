package gumdrop.test.json;

import gumdrop.json.ListSetters;

class FullNamePersonListSetters extends ListSetters<FullNamePerson> {

  FullNamePersonListSetters() {
    super(new FullNamePersonSetters());
  }

}
