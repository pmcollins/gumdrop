package gumdrop.json.v2;

import java.util.function.BiConsumer;

public class StringSetterNode<T> extends AbstractChainable {

  private final T t;
  private final BiConsumer<T, String> setter;

  StringSetterNode(T t, BiConsumer<T, String> setter) {
    this.t = t;
    this.setter = setter;
  }

  @Override
  public void accept(String s) {
    setter.accept(t, s);
  }

}
