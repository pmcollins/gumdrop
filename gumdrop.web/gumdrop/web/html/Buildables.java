package gumdrop.web.html;

public class Buildables implements Buildable {

  private final Buildable[] buildables;

  public Buildables(Buildable... buildables) {
    this.buildables = buildables;
  }

  @Override
  public void build(StringBuilder sb) {
    for (Buildable buildable : buildables) {
      buildable.build(sb);
    }
  }

}
