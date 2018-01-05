package gumdrop.server.test.dispatch;

abstract class RestHandler {

  private String[] matches;

  String[] getMatches() {
    return matches;
  }

  void setMatches(String[] matches) {
    this.matches = matches;
  }

}
