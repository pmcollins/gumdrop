package gumdrop.web.controller;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Route {

  private final Pattern pattern;
  private final Supplier<Controller> supplier;

  Route(String strPattern, Supplier<Controller> supplier) {
    this.pattern = Pattern.compile(strPattern);
    this.supplier = supplier;
  }

  Optional<Controller> attemptMatch(String path) {
    Matcher matcher = pattern.matcher(path);
    if (matcher.matches()) {
      Controller controller = supplier.get();
      int groupCount = matcher.groupCount();
      String[] matches = new String[groupCount];
      for (int i = 0; i < groupCount; i++) {
        String group = matcher.group(i + 1);
        matches[i] = group;
      }
      controller.setPathArgs(matches);
      return Optional.of(controller);
    } else {
      return Optional.empty();
    }
  }

}
