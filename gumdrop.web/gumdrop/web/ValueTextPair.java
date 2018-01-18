package gumdrop.web;

public class ValueTextPair {

  private String value;
  private String text;

  public ValueTextPair(Enum<?> value, String text) {
    this(value.toString(), text);
  }

  public ValueTextPair(String value, String text) {
    this.setValue(value);
    this.setText(text);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
