package gumdrop.web.html;

public abstract class SimpleView<T> implements View<T> {

  protected abstract Buildable buildable(T t);

  @Override
  public final void accept(StringBuilder sb, T t) {
    buildable(t).build(sb);
  }

}
