package gumdrop.test.fake;

import gumdrop.json.StringConverter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantConverter implements StringConverter<Instant> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

  @Override
  public String toString(Instant instant) {
    return formatter.format(instant);
  }

  @Override
  public Instant fromString(String s) {
    return Instant.from(formatter.parse(s));
  }

}
