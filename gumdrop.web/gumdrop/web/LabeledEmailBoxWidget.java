package gumdrop.web;

public class LabeledEmailBoxWidget extends LabeledInputWidget {

  public LabeledEmailBoxWidget(Enum<?> name, String label) {
    super("email", name, label);
  }

  public LabeledEmailBoxWidget(String name, String label) {
    super("email", name, label);
  }

}
