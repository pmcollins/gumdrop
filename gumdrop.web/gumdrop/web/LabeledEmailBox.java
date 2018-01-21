package gumdrop.web;

public class LabeledEmailBox extends LabeledInputWidget {

  public LabeledEmailBox(Enum<?> name, String label) {
    super("email", name, label);
  }

  public LabeledEmailBox(String name, String label) {
    super("email", name, label);
  }

}
