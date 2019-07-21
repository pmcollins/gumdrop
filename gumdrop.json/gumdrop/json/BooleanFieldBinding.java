package gumdrop.json;

import java.util.function.BiConsumer;

public class BooleanFieldBinding<T> extends FieldBinding<T, Boolean> {

  public BooleanFieldBinding(String name, BiConsumer<T, Boolean> setter) {
    super(name, setter, BooleanNode::new);
  }

}

