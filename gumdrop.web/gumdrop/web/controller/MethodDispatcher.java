package gumdrop.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MethodDispatcher {

  private static final String DIGITS_PATTERN = "(\\\\d+)";
  private static final String WILDCARD_PATTERN = "(.+)";

  private final List<Route> routes = new ArrayList<>();

  public void register(String strPattern, Supplier<Controller> controllerSupplier) {
    String regexPattern = getRegexPattern(strPattern);
    register(new Route(regexPattern, controllerSupplier));
  }

  public void register(Route route) {
    routes.add(route);
  }

  public static String getRegexPattern(String strPattern) {
    return strPattern.replaceAll("#", DIGITS_PATTERN).replaceAll("\\*", WILDCARD_PATTERN);
  }

  public Optional<Controller> getController(String path) {
    for (Route route : routes) {
      Optional<Controller> controller = route.attemptMatch(path);
      if (controller.isPresent()) {
        return controller;
      }
    }
    return Optional.empty();
  }

}
