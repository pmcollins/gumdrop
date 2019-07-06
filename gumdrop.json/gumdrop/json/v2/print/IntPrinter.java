package gumdrop.json.v2.print;

public class IntPrinter extends JsonPrinter<Integer> {

  @Override
  public void print(StringBuilder sb, Integer integer) {
    sb.append(integer);
  }

}
