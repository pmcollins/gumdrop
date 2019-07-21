package gumdrop.json.print;

public class StringPrinter extends JsonPrinter<String> {

  @Override
  public void printNonNull(StringBuilder sb, String s) {
    sb.append('"').append(s).append('"');
  }

}
