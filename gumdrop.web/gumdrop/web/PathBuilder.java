package gumdrop.web;

public class PathBuilder {

  private final String[] parts;

  public PathBuilder(String path) {
    parts = path.split("#");
    String lastPart = parts[parts.length - 1];
    if (lastPart.endsWith("*")) {
      parts[parts.length - 1] = lastPart.substring(0, lastPart.length() - 1);
    }
  }

  public String build(Object... args) {
    StringBuilder sb = new StringBuilder(parts[0]);
    for (int i = 0; ; i++) {
      if (args.length == i) break;
      sb.append(args[i]);
      if (parts.length == i+1) break;
      sb.append(parts[i+1]);
    }
    return sb.toString();
  }

}
