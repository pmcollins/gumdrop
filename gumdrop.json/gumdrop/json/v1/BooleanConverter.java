package gumdrop.json.v1;

public class BooleanConverter implements StringConverter<Boolean> {

  @Override
  public String toString(Boolean b) {
    return String.valueOf(b);
  }

  @Override
  public Boolean fromString(String s) {
    return "null".equals(s) ? null : Boolean.valueOf(s);
  }

}
