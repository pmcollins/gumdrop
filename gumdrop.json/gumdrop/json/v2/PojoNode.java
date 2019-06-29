package gumdrop.json.v2;

import gumdrop.json.v2.common.Node;
import gumdrop.json.v2.common.SupplierNode;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PojoNode<T> extends SupplierNode<T> {

  private final Function<String, BiConsumer<T, String>> setterFcn;

  protected PojoNode(T t, Function<String, BiConsumer<T, String>> setterFcn) {
    super(t);
    this.setterFcn = setterFcn;
  }

  @Override
  public final Node next(String key) {
    BiConsumer<T, String> setter = setterFcn.apply(key);
    T t = get();
    return new StringSetterNode<>(t, setter);
  }

}
