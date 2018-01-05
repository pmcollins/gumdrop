package gumdrop.server.nio;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Route {

  private final Pattern pattern;
  private final Supplier<RestHandler> supplier;

  Route(String strPattern, Supplier<RestHandler> supplier) {
    this.pattern = Pattern.compile(strPattern);
    this.supplier = supplier;
  }

  Optional<RestHandler> newHandler(String path) {
    Matcher matcher = pattern.matcher(path);
    if (matcher.matches()) {
      RestHandler restHandler = supplier.get();
      int groupCount = matcher.groupCount();
      String[] matches = new String[groupCount];
      for (int i = 0; i < groupCount; i++) {
        String group = matcher.group(i + 1);
        matches[i] = group;
      }
      restHandler.setMatches(matches);
      return Optional.of(restHandler);
    } else {
      return Optional.empty();
    }
  }

}
