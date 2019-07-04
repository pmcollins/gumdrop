package gumdrop.json.v2.print;

import gumdrop.json.v2.print.JsonPrinter;

public class StringPrinter implements JsonPrinter<String> {

  @Override
  public void print(StringBuilder sb, String s) {
    sb.append('"').append(s).append('"');
  }

}
