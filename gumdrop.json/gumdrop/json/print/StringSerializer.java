package gumdrop.json.print;

public class StringSerializer extends JsonSerializer<String> {

  @Override
  public void nonNullToJson(String s, StringBuilder sb) {
    sb.append('"').append(s).append('"');
  }

}
