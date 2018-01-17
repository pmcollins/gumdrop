package gumdrop.web;

public class PasswordBox extends InputWidget {

  public PasswordBox(Enum<?> name, String label) {
    this(name.toString().toLowerCase(), label);
  }

  public PasswordBox(String name, String label) {
    super("password", name, label);
  }

}
