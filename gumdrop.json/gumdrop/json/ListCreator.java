package gumdrop.json;

import gumdrop.common.Creator;

import java.util.ArrayList;
import java.util.List;

public class ListCreator<T> extends Creator<List<T>> {

  static final String ARRAY_ADD_KEY = "add";

  protected ListCreator(Creator<T> refs) {
    super(ArrayList::new);
    addMember(ARRAY_ADD_KEY, List::add, refs);
  }

}
