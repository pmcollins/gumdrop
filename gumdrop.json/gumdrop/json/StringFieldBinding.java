package gumdrop.json;

import java.util.function.BiConsumer;

public class StringFieldBinding<T> extends FieldBinding<T, String> {

  public StringFieldBinding(String name, BiConsumer<T, String> setter) {
    super(name, setter, StringNode::new);
  }

}
