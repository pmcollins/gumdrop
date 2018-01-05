package gumdrop.json.test;

import gumdrop.json.ListSetters;

class FullNamePersonListSetters extends ListSetters<FullNamePerson> {

  FullNamePersonListSetters() {
    super(new FullNamePersonSetters());
  }

}
