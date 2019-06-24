package gumdrop.json.v1;

public class IntegerConverter implements StringConverter<Integer> {

  @Override
  public String toString(Integer i) {
    return i == null ? "null" : String.valueOf(i);
  }

  @Override
  public Integer fromString(String s) {
    return "null".equals(s) ? null : Integer.parseInt(s);
  }

}
