package gumdrop.json.v1;

import gumdrop.common.builder.v1.Builder;

import java.util.ArrayList;
import java.util.List;

public class ListBuilder<T> extends Builder<List<T>> {

  static final String ARRAY_ADD_KEY = "add";

  protected ListBuilder(Builder<T> refs) {
    super(ArrayList::new);
    addBuilder(ARRAY_ADD_KEY, List::add, refs);
  }

}