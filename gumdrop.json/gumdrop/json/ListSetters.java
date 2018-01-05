package gumdrop.json;

import java.util.ArrayList;
import java.util.List;

public class ListSetters<T> extends Setters<List<T>> {

  static final String ARRAY_ADD_KEY = "add";

  protected ListSetters(Setters<T> refs) {
    super(ArrayList::new);
    addMember(ARRAY_ADD_KEY, List::add, refs);
  }

}
