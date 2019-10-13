package gumdrop.json.print;

public class IntSerializer extends JsonSerializer<Integer> {

  @Override
  public void nonNullToJson(Integer integer, StringBuilder sb) {
    sb.append(integer);
  }

}
