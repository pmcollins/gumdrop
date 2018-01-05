package gumdrop.server.nio;

import java.util.*;
import java.util.function.Supplier;

public class MethodDispatcher {

  private static final String DIGITS_PATTERN = "(\\\\d+)";

  private final List<Route> routes = new ArrayList<>();

  public void register(String strPattern, Supplier<RestHandler> handler) {
    routes.add(new Route(getRegexPattern(strPattern), handler));
  }

  public static String getRegexPattern(String strPattern) {
    return strPattern.replaceAll("#", DIGITS_PATTERN);
  }

  public Optional<RestHandler> getHandler(String path) {
    for (Route route : routes) {
      Optional<RestHandler> restHandler = route.newHandler(path);
      if (restHandler.isPresent()) {
        return restHandler;
      }
    }
    return Optional.empty();
  }

}
