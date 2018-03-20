package gumdrop.web.html;

public abstract class Widget implements Buildable {

  protected abstract Buildable getBuildable();

  @Override
  public final void build(StringBuilder sb) {
    getBuildable().build(sb);
  }

}
