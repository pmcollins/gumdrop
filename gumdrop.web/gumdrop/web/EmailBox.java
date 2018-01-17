package gumdrop.web;

public class EmailBox extends InputWidget {

  public EmailBox(Enum<?> name, String label) {
    super("email", name, label);
  }

  public EmailBox(String name, String label) {
    super("email", name, label);
  }

  public EmailBox() {
    setType("email");
  }

}
