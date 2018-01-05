package gumdrop.server.nio;

import java.nio.ByteBuffer;

public abstract class RestHandler {

  private String[] matches;
  private String request;

  public String[] getMatches() {
    return matches;
  }

  void setMatches(String[] matches) {
    this.matches = matches;
  }

  void setRequest(String request) {
    this.request = request;
  }

  String getRequest() {
    return request;
  }

  protected abstract ByteBuffer render();

}
