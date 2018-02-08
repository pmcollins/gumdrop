package gumdrop.web;

public class LabeledPasswordBoxWidget extends LabeledInputWidget {

  public LabeledPasswordBoxWidget(Enum<?> name, String label) {
    this(name.toString().toLowerCase(), label);
  }

  public LabeledPasswordBoxWidget(String name, String label) {
    super("password", name, label);
  }

}
