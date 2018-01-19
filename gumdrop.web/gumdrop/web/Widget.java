package gumdrop.web;

public abstract class Widget implements Buildable {

  protected abstract Buildable getRoot();

  @Override
  public final void build(StringBuilder sb) {
    getRoot().build(sb);
  }

}
