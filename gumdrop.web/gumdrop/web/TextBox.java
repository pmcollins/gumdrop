package gumdrop.web;

public class TextBox extends InputWidget {

  public TextBox(Enum<?> name, String label) {
    super("text", name, label);
  }

  public TextBox(String name, String label) {
    super("text", name, label);
  }

  public TextBox() {
    setType("text");
  }

}
