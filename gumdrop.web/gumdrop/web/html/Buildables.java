package gumdrop.web.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Buildables implements Buildable {

  private final List<Buildable> buildables = new ArrayList<>();

  public Buildables(Buildable... buildables) {
    this.buildables.addAll(Arrays.asList(buildables));
  }

  public void add(Buildable buildable) {
    buildables.add(buildable);
  }

  @Override
  public void build(StringBuilder sb) {
    for (Buildable buildable : buildables) {
      buildable.build(sb);
    }
  }

}
