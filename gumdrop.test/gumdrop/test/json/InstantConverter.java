package gumdrop.test.json;

import gumdrop.json.Converter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantConverter implements Converter<Instant> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

  @Override
  public String convertToString(Instant instant) {
    return formatter.format(instant);
  }

  @Override
  public Instant convertFromString(String s) {
    return Instant.from(formatter.parse(s));
  }

}
