package gumdrop.web;

public class LabeledPasswordBox extends LabeledInputWidget {

  public LabeledPasswordBox(Enum<?> name, String label) {
    this(name.toString().toLowerCase(), label);
  }

  public LabeledPasswordBox(String name, String label) {
    super("password", name, label);
  }

}
