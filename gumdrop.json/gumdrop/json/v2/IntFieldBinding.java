package gumdrop.json.v2;

import java.util.function.BiConsumer;

public class IntFieldBinding<T> extends FieldBinding<T, Integer> {

  public IntFieldBinding(String name, BiConsumer<T, Integer> setter) {
    super(name, setter, IntNode::new);
  }

}
