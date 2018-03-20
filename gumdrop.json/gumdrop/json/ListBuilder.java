package gumdrop.json;

import gumdrop.common.builder.Builder;

import java.util.ArrayList;
import java.util.List;

public class ListBuilder<T> extends Builder<List<T>> {

  static final String ARRAY_ADD_KEY = "add";

  protected ListBuilder(Builder<T> refs) {
    super(ArrayList::new);
    addMember(ARRAY_ADD_KEY, List::add, refs);
  }

}
