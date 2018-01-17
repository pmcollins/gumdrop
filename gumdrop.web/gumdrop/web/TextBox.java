package gumdrop.web;

public class TextBox extends InputWidget {

  public TextBox(String name, String label) {
    super("text", name, label);
  }

  public TextBox() {
    setType("text");
  }

}
