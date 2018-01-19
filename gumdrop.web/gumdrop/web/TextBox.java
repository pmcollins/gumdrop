package gumdrop.web;

public class TextBox extends LabeledInputWidget {

  public TextBox(Enum<?> name, String label) {
    super("text", name, label);
  }

  public TextBox(String name, String label) {
    super("text", name, label);
  }

}
