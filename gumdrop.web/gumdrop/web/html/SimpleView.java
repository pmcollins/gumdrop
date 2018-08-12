package gumdrop.web.html;

public abstract class SimpleView<T> implements IView<T> {

  protected abstract Buildable getBuildable(T t);

  @Override
  public final void accept(StringBuilder sb, T t) {
    getBuildable(t).build(sb);
  }

}
