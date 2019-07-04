package gumdrop.test.json.v2;

class StringPrinter implements JsonPrinter<String> {

  @Override
  public void print(StringBuilder sb, String s) {
    sb.append('"').append(s).append('"');
  }

}
