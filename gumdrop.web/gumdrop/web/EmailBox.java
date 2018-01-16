package gumdrop.web;

public class EmailBox extends InputWidget {

  public EmailBox(String label, Enum<?> name) {
    super("email", label, name);
  }

  public EmailBox(String label, String name) {
    super("email", label, name);
  }

  public EmailBox() {
    setType("email");
  }

}
