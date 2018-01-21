package gumdrop.web;

public class LabeledTextBox extends LabeledInputWidget {

  public LabeledTextBox(Enum<?> name, String label) {
    super("text", name, label);
  }

  public LabeledTextBox(String name, String label) {
    super("text", name, label);
  }

}
