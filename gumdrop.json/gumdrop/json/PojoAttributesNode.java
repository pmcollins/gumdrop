package gumdrop.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PojoAttributesNode<T> extends Node<T> {

  private final Map<String, FieldBinding<T, ?>> map = new HashMap<>();

  PojoAttributesNode(T t, List<FieldBinding<T, ?>> bindings) {
    super(t);
    for (FieldBinding<T, ?> binding : bindings) {
      map.put(binding.getName(), binding);
    }
  }

  @Override
  public Chainable next(String key) {
    FieldBinding<T, ?> binding = map.get(key);
    return binding.buildNode(getValue());
  }

}
