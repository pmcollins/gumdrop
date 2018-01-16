package gumdrop.web;

public class PasswordBox extends InputWidget {

  public PasswordBox(String label, Enum<?> name) {
    this(label, name.toString().toLowerCase());
  }

  public PasswordBox(String label, String name) {
    super("password", label, name);
  }

}
