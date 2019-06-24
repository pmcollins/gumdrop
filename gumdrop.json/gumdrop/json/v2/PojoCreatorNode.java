package gumdrop.json.v2;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PojoCreatorNode<T> extends BaseCreatorNode<T> {

  private final Function<String, BiConsumer<T, String>> setterFcn;

  protected PojoCreatorNode(T t, Function<String, BiConsumer<T, String>> setterFcn) {
    super(t);
    this.setterFcn = setterFcn;
  }

  @Override
  public final CreatorNode next(String key) {
    BiConsumer<T, String> setter = setterFcn.apply(key);
    T t = get();
    return new StringSetterNode<>(t, setter);
  }

}
