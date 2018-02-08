package gumdrop.web;

public class LabeledTextBoxWidget extends LabeledInputWidget {

  public LabeledTextBoxWidget(Enum<?> name, String label) {
    super("text", name, label);
  }

  public LabeledTextBoxWidget(String name, String label) {
    super("text", name, label);
  }

}
