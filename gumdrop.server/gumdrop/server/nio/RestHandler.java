package gumdrop.server.nio;

public abstract class RestHandler {

  private String[] matches;

  public String[] getMatches() {
    return matches;
  }

  void setMatches(String[] matches) {
    this.matches = matches;
  }

}
