package gumdrop.server.test.dispatch;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;

class MethodDispatcher {

  private static final String DIGITS_PATTERN = "(\\\\d+)";

  private final List<Route> routes = new ArrayList<>();

  void register(String strPattern, Supplier<RestHandler> handler) {
    routes.add(new Route(getRegexPattern(strPattern), handler));
  }

  static String getRegexPattern(String strPattern) {
    return strPattern.replaceAll("#", DIGITS_PATTERN);
  }

  Optional<RestHandler> getHandler(String path) {
    for (Route route : routes) {
      Optional<RestHandler> restHandler = route.newHandler(path);
      if (restHandler.isPresent()) {
        return restHandler;
      }
    }
    return Optional.empty();
  }

}
